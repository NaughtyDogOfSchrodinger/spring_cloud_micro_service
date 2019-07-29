package com.jianghu.mscore.web.interceptor;

import com.google.common.collect.Maps;
import com.jianghu.mscore.exception.BaseException;
import com.jianghu.mscore.web.config.AppThreadCache;
import com.jianghu.mscore.web.config.properties.ApplicationProperties;
import com.jianghu.mscore.web.constant.AuthUrl;
import com.jianghu.mscore.web.exception.AuthenticationException;
import com.jianghu.mscore.web.service.AbstractService;
import com.jianghu.mscore.web.util.HttpUtil;
import com.jianghu.mscore.web.util.RestAuthResultWrapper;
import com.jianghu.mscore.web.util.RmcResultWrapper;
import com.jianghu.mscore.web.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 授权信息处理类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
@Service
@EnableConfigurationProperties({ApplicationProperties.class})
public class AuthenticationHandler extends AbstractService {


    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Resource
    private ApplicationProperties applicationProperties;

    private RestTemplate restTemplate = new RestTemplate();

    @Resource
    private AuthUrl authUrl;

    public String check(HttpServletRequest request, Object o) {
        ResultVo resultVo = HttpUtil.HttpRequest(authUrl.check(),
                HttpMethod.POST.name(),
                buildJsonString(o),
                request);
        return resultVo.getData();
    }


    /**
     * 获取授权
     *
     * @return the authentication
     */
    public Map getAuthentication() {
        try {
            HttpEntity entity = getHttpEntity();
            ResponseEntity<RmcResultWrapper> result = restTemplate.exchange(applicationProperties.getDomain() + applicationProperties.getAuthenticationUrl(), HttpMethod.GET, entity, RmcResultWrapper.class);
            if ((Objects.requireNonNull(result.getBody())).status == 1L) {
                return (Map<String, Object>) (result.getBody()).getData();
            } else {
                throw new AuthenticationException((result.getBody()).getMsg());
            }
        } catch (HttpClientErrorException e) {
            String errorMsg = String.format("获取认证信息失败[%s:%s]错误", e.getStatusCode(), e.getMessage());
            logger.info(errorMsg);
            throw new AuthenticationException(errorMsg);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 发起授权
     *
     * @param requestUrl the request url
     */
    public void getAuthorization(String requestUrl) {
        try {
            HttpEntity entity = getHttpEntity();
            Map<String, String> requestParams = Maps.newHashMap();
            requestParams.put("requestUrl", requestUrl);
            String newUrl = String.format("%s?requestUrl={requestUrl}", applicationProperties.getAuthenticationUrl());
            ResponseEntity<RestAuthResultWrapper> result = restTemplate.exchange(newUrl, HttpMethod.GET, entity, RestAuthResultWrapper.class, requestParams);
            if ((Objects.requireNonNull(result.getBody())).status == 1L) {
                logger.info((result.getBody()).getMsg());
            } else {
                throw new AuthenticationException((result.getBody()).getMsg());
            }
        } catch (HttpClientErrorException e) {
            String errorMsg = String.format("获取授权信息失败[%s:%s]", e.getStatusCode(), e.getMessage());
            this.logger.info(errorMsg);
            throw new AuthenticationException(errorMsg);
        } catch (BaseException e) {
            this.logger.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw e;
        }
    }

    private HttpEntity getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(applicationProperties.getSessionKey(), AppThreadCache.getSessionId());
        httpHeaders.set("user-agent", AppThreadCache.getRequestHeader("user-agent"));
        return new HttpEntity(httpHeaders);
    }
}
