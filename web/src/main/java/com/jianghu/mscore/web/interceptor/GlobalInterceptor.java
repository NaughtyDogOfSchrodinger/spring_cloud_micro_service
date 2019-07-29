package com.jianghu.mscore.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jianghu.mscore.web.config.AppThreadCache;
import com.jianghu.mscore.web.config.properties.ApplicationProperties;
import com.jianghu.mscore.web.constant.PlatformEnum;
import com.jianghu.mscore.web.exception.AuthenticationException;
import com.jianghu.mscore.web.util.AjaxResult;
import com.jianghu.mscore.web.util.HttpUtil;
import com.jianghu.mscore.web.vo.CheckAuthorityVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.jianghu.mscore.web.constant.PropertiesKey.AUTHENTICATION_KEY;
import static com.jianghu.mscore.web.constant.PropertiesKey.DEFAULT_PROPERTIES_KEY;


/**
 * 全局拦截器
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class GlobalInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);
    @Resource
    private ApplicationProperties applicationProperties;



    /**
     * 授权处理类
     */

    @Autowired(required = false)
    public AuthenticationHandler authenticationHandler;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try {

            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");

            httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
            //授权相关配置存入线程变量
            AppThreadCache.getExtraData().put(DEFAULT_PROPERTIES_KEY, applicationProperties);

            AppThreadCache.setStartTime();
            Enumeration headerNames = httpServletRequest.getHeaderNames();

            //请求头信息存入线程变量
            String key;
            String value;
            while (headerNames.hasMoreElements()) {
                key = (String) headerNames.nextElement();
                value = httpServletRequest.getHeader(key);
                if ("user-agent".equals(key) && value != null) {
                    AppThreadCache.setPlatform(getPlatform(value));
                }
                AppThreadCache.putRequestHeader(key, value);
            }

            String ip;
            //ip存入线程变量
            if (httpServletRequest.getHeader("x-forwarded-for") == null) {
                ip = httpServletRequest.getRemoteAddr();
            } else {
                ip = httpServletRequest.getHeader("x-forwarded-for");
            }
            AppThreadCache.setXForwardedFor(ip);

            //获取session
            Object session = httpServletRequest.getSession().getAttribute(applicationProperties.getSessionKey());
            //获取header中session
            if (session == null) {
                session = httpServletRequest.getHeader(applicationProperties.getSessionKey());
            }
            //session和accessToken存入线程变量,session pc用，accessToken app用
            AppThreadCache.setSessionId(session != null ? (String) session : null);
            //日志开始
            logBegin(httpServletRequest);
            String requestUri = httpServletRequest.getRequestURI();
            boolean skip = false;
            //跳过url关键字
            for (String containUrl : applicationProperties.getContainUrl()) {
                if (requestUri.contains(containUrl.toLowerCase())) {
                    skip = true;
                    break;
                }
            }
            //授权url，登录url直接跳过
            if (!skip && !Objects.equals(requestUri, applicationProperties.getLoginUrl())
                    && !Objects.equals(requestUri, applicationProperties.getAuthenticationUrl())
                    && !Objects.equals(requestUri, applicationProperties.getAuthenticationUrl())) {
                String uri = getUri(httpServletRequest).toLowerCase();

                boolean isExcludeAuth = false;
                //跳过url关键字
                for (String containUrl : applicationProperties.getContainUrl()) {
                    if (uri.contains(containUrl.toLowerCase())) {
                        isExcludeAuth = true;
                        break;
                    }
                }
                //跳过url前缀
                if (!isExcludeAuth) {
                    for (String prefix : applicationProperties.getExcludePrefix()) {
                        if (uri.startsWith(prefix.toLowerCase())) {
                            isExcludeAuth = true;
                            break;
                        }
                    }
                }
                //跳过url
                if (!isExcludeAuth) {
                    for (String url : applicationProperties.getExcludeUrls()) {
                        if (Objects.equals(uri, url.toLowerCase())) {
                            isExcludeAuth = true;
                            break;
                        }
                    }
                }
                //若无访问权限，授权后可访问
                if (!isExcludeAuth) {
                    //若无令牌，抛异常
                    if (session == null) {
                        throw new AuthenticationException("无权限访问该资源");
                    } //获取授权信息存入线程变量
                    checkAuthority(httpServletRequest, httpServletResponse, requestUri);
                    //若有访问权限，并且令牌非空，缓存认证信息
                } else if (session != null) {
                    checkAuthority(httpServletRequest, httpServletResponse, requestUri);
                }
                //若有访问权限，令牌为空，继续

            }
            return true;
        } catch (Exception e) {
            this.logEnd(httpServletRequest);
            String loginUrl = applicationProperties.getLoginUrl();
            if (StringUtils.isEmpty(loginUrl)) {
                JSONObject json = new JSONObject(AjaxResult.createAjaxFailMap(e.getMessage()));
                HttpUtil.sendJson(httpServletResponse, json);
            } else {
                httpServletResponse.sendRedirect(loginUrl);
            }
            return false;

        }
    }

    private void redirectLogin(HttpServletResponse response) throws IOException {
        String loginUrl = applicationProperties.getLoginUrl();
        if (!StringUtils.isEmpty(applicationProperties.getLoginUrl())) {
            response.sendRedirect(loginUrl);
        }
    }

    private void checkAuthority(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        Map authentication = authenticationHandler.getAuthentication();
        if (authentication == null) {
            redirectLogin(response);
            return;
        }
        if (!Objects.equals(url, "/index") && "false".equals(authenticationHandler.check(request, new CheckAuthorityVo(authentication.get("staffId") != null ? Integer.valueOf(authentication.get("staffId").toString()) : 0, url)))) {
            throw new AuthenticationException("无权限访问该资源");
        }
        AppThreadCache.setExtraData(new ConcurrentHashMap(authentication));


    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            o, ModelAndView modelAndView) throws Exception {
        this.logEnd(httpServletRequest);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, Exception e) throws Exception {
    }

    private String getUri(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceAll(";.*", "");
        return uri.substring(request.getContextPath().length());
    }

    private void logBegin(HttpServletRequest httpServletRequest) {
        this.logger.info("service begin => uri[{}:{}], session[{}:{}],  x_forwarded_for[{}], platForm[{}]",
                httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
                ((ApplicationProperties) AppThreadCache.getExtraData(DEFAULT_PROPERTIES_KEY)).getSessionKey(), AppThreadCache.getSessionId(),
                AppThreadCache.getXForwardedFor(),
                AppThreadCache.getPlatform() == null ? "" : AppThreadCache.getPlatform().getName());
    }

    private void logEnd(HttpServletRequest httpServletRequest) {
        long timeCost = System.currentTimeMillis() - AppThreadCache.getStartTime();
        this.logger.info("service end   => uri[{}:{}], times[{} ms]", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), timeCost);
    }

    private static PlatformEnum getPlatform(String agent) {
        if (agent.contains("iPhone") || agent.contains("iPod") || agent.contains("iPad") || agent.contains("iOS")) {
            return PlatformEnum.IOS;
        } else if (agent.contains("Android") || agent.contains("Linux")) {
            return PlatformEnum.Android;
        } else {
            return PlatformEnum.PC;
        }
    }
}
