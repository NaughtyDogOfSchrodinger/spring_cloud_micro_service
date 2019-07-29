//package com.jianghu.mscore.core.mvc.web.interceptor;
//
//import com.alibaba.fastjson.JSON;
//import com.jianghu.mscore.context.config.AppThreadCache;
//import com.jianghu.mscore.context.config.ApplicationConstant;
//import com.jianghu.mscore.context.constant.TokenManager;
//import com.jianghu.mscore.context.exception.RemoteException;
//import com.jianghu.mscore.context.remote.RmcInvoker;
//import com.jianghu.mscore.util.BeanUtil;
//import com.jianghu.mscore.util.StringUtil;
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//import com.netflix.hystrix.HystrixCommandKey;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * 鉴权拦截器
// * 请求时间记录
// *
// * @author hujiang.
// * @version 1.0
// * @since 2019.04.26
// */
//@Component
//public class DefaultAclInterceptor implements HandlerInterceptor {
//    private final Logger logger = LoggerFactory.getLogger(DefaultAclInterceptor.class);
//    @Resource
//    private ApplicationConstant applicationConstant;
//    @Autowired
//    @LoadBalanced
//    private RestTemplate restTemplate;
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        AppThreadCache.setStartTime();
//
//        //请求源信息
//        String rmtAddr;
//        if (httpServletRequest.getHeader(TokenManager.X_FORWARDED_FOR) == null) {
//            rmtAddr = httpServletRequest.getRemoteAddr();
//        } else {
//            rmtAddr = httpServletRequest.getHeader(TokenManager.X_FORWARDED_FOR);
//        }
//
//        AppThreadCache.setXForwardedFor(rmtAddr);
//        AppThreadCache.setReferer(httpServletRequest.getHeader(TokenManager.REFERER));
//
//        //LICENSE信息
//        AppThreadCache.setAppId(httpServletRequest.getHeader(TokenManager.APP_ID));
//        AppThreadCache.setAppKey(httpServletRequest.getHeader(TokenManager.APP_KEY));
//        AppThreadCache.setAppSecret(httpServletRequest.getHeader(TokenManager.APP_SECRET));
//        AppThreadCache.setAppSignature(httpServletRequest.getHeader(TokenManager.APP_SIGNATURE));
//
//        //令牌信息
//        AppThreadCache.setSsId(httpServletRequest.getHeader(applicationConstant.ssId));
//        AppThreadCache.setVfId(httpServletRequest.getHeader(applicationConstant.vfId));
//        String scopeId;
//        AppThreadCache.setScopeId(StringUtil.isEmpty(scopeId = httpServletRequest.getHeader(applicationConstant.scopeId)) ? "1" : scopeId);
//
//        //服务信息
//        AppThreadCache.setServiceName(applicationConstant.serviceName);
//        AppThreadCache.setProductName(applicationConstant.productName);
//        Map<String, Object> auth;
//        if (!Objects.equals("cs-authority", applicationConstant.serviceName)
//                && StringUtils.isNotEmpty(AppThreadCache.getSsId())
//                && Objects.equals((auth = getAuth(HashMap.class)).get("status"), 1) && auth.get("data") != null) {
//            AppThreadCache.setExtraData(new ConcurrentHashMap<>((Map)auth.get("data")));
//        }
//
//
//        logger.info("======================REQUEST INFORMATION BEGIN:[{}:{}]===================================================", httpServletRequest.getMethod(), getUri(httpServletRequest));
//        logger.info("SERVICE ACCESS LICENSE INFORMATION:APP_ID[{}],APP_KEY[{}],APP_SECRET[{}],APP_SIGNATURE[{}]",
//                AppThreadCache.getAppId(), AppThreadCache.getAppKey(), AppThreadCache.getAppSecret(), AppThreadCache.getAppSignature());
//        logger.debug("SERVICE ACCESS SOURCE INFORMATION:METHOD[{}],URI[{}],X_FORWARDED_FOR[{}],REFERER[{}]", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), AppThreadCache.getXForwardedFor(), AppThreadCache.getReferer());
//
//        //访问许可
//        logger.info("SERVICE ACCESS AUTHORIZATION INFORMATION:PRODUCT[{}],SERVICE[{}|{}],SS_ID[{}:{}],VF_ID[{}:{}]", AppThreadCache.getProductName(), AppThreadCache.getServiceName(), AppThreadCache.getVersion(), applicationConstant.ssId, AppThreadCache.getSsId(), applicationConstant.vfId, AppThreadCache.getVfId());
//        logger.info("--------------------------------------------------------------------------------------------------------");
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        logger.info("--------------------------------------------------------------------------------------------------------");
//        long time = AppThreadCache.getStartTime();
//        if (time > 0) {
//            long timeCost = System.currentTimeMillis() - time;
//            logger.info("SERVICE ACCESS TIMES:URI[{}],TIMES[{} MS]", httpServletRequest.getRequestURI(), timeCost);
//        }
//        logger.info("======================REQUEST INFORMATION  END [{}:{}]===================================================", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//
//    private String getUri(HttpServletRequest request) {
//        String uri = request.getRequestURI().replaceAll(";.*", "");
//        return uri.substring(request.getContextPath().length());
//    }
//
//    private <T> T getAuth(Class<T> clazz) {
//        Map<String, Object> requestHeaders = new HashMap<>();
//        //封装接入许可
//        requestHeaders.put(TokenManager.X_FORWARDED_FOR, AppThreadCache.getXForwardedFor());
//        requestHeaders.put(TokenManager.REFERER, AppThreadCache.getReferer());
//        requestHeaders.put(TokenManager.APP_ID, AppThreadCache.getAppId());
//        requestHeaders.put(TokenManager.APP_KEY, AppThreadCache.getAppKey());
//        requestHeaders.put(TokenManager.APP_SECRET, AppThreadCache.getAppSecret());
//        requestHeaders.put(TokenManager.APP_SIGNATURE, AppThreadCache.getAppSignature());
//
//        //封装访问许可
//        requestHeaders.put(applicationConstant.ssId, AppThreadCache.getSsId());
//        requestHeaders.put(applicationConstant.vfId, AppThreadCache.getVfId());
//        requestHeaders.put(applicationConstant.scopeId, AppThreadCache.getScopeId() == null ? "1" : AppThreadCache.getScopeId());
//        return new HystrixCommand<T>(HystrixCommand.Setter
//                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RMC:" + "cs-authority"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("cs-authority" + ":" + "/auth/authentication"))) {
//            @Override
//            protected T run() throws Exception {
//
//                /*报文头处理*/
//                HttpHeaders httpHeaders = new HttpHeaders();
//                for (Map.Entry<String, Object> entry : requestHeaders.entrySet()) {
//                    httpHeaders.add(entry.getKey(), (String) entry.getValue());
//                }
//                httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
//                HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
//                ResponseEntity<T> results = restTemplate.exchange("http://cs-authority/auth/authentication", HttpMethod.GET, entity, clazz);
//                /*调用处理*/
//                return results.getBody();
//            }
//
//            @Override
//            protected T getFallback() {
//                throw new RemoteException(String.format("Msp remote service call hystrix exception：[%s]:[%s]", "cs-authority", "/auth/authentication"));
//            }
//        }.execute();
//    }
//}
//
