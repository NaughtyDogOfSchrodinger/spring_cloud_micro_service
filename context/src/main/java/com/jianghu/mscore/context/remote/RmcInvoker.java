package com.jianghu.mscore.context.remote;

import com.alibaba.fastjson.JSON;
import com.jianghu.mscore.context.config.AppThreadCache;
import com.jianghu.mscore.context.config.ApplicationConstant;
import com.jianghu.mscore.context.config.RemoteServiceConfigurer;
import com.jianghu.mscore.context.constant.TokenManager;
import com.jianghu.mscore.context.exception.RemoteException;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 远程服务调用集成Hystrix，Ribbon， RestTemplate，服务需要在应用配置文件中进行配置。
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
@Service
@Scope("prototype")
public class RmcInvoker {
    /**
     * The Logger.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The Remote service configurer.
     */
    @Autowired
    RemoteServiceConfigurer remoteServiceConfigurer;

    @Resource
    private ApplicationConstant applicationConstant;

    @Autowired
    @LoadBalanced
    private RestTemplate lbRestTemplate;


    /**
     * 变参变量uriVariables：0: params,1:headers,2:uriParam
     */
    private Integer variableCount = 3;

    /**
     * Invoke t.
     *
     * @param <T>          the type parameter
     * @param api          the api
     * @param responseType the response type
     * @param uriVariables the uri variables
     * @return the t
     * @since 2019.06.11
     */
    public <T> T invoke(String api, Class<T> responseType, Map<String, Object>... uriVariables) {

        // 组拼远程调用API信息
        HashMap<String, String> apiWrapperObj = new LinkedHashMap<>();
        Map serviceConfig = remoteServiceConfigurer.getRemoteServiceConfigNode();
        for (Object o : serviceConfig.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object key = entry.getKey();
            Map serviceNode = (Map) entry.getValue();
            if (serviceNode.containsKey("import")) {
                Map importNode = (Map) serviceNode.get("import");
                if (importNode != null) {
                    if (importNode.containsKey(api)) {
                        Map apiNode = (Map) importNode.get(api);
                        apiWrapperObj.put("service_id", (String) serviceNode.get("id"));
                        apiWrapperObj.put("service_name", (String) serviceNode.get("name"));
                        apiWrapperObj.put("service_comment", (String) serviceNode.get("comment"));
                        apiWrapperObj.put("api_path", (String) apiNode.get("api"));
                        apiWrapperObj.put("api_method", (String) apiNode.get("method"));
                        apiWrapperObj.put("api_name", (String) apiNode.get("name"));
                        apiWrapperObj.put("api_comment", (String) apiNode.get("comment"));
                        break;
                    }
                }
            }
        }
        if (apiWrapperObj.isEmpty()) {
            throw new RemoteException(String.format("配置文件中没有导入API：[%s]", api));
        }

        if (uriVariables.length > variableCount) {
            throw new RemoteException("变参变量uriVariables：0: params,1:headers,2:uriParam");
        }

        //组拼请求参数信息
        String serviceUrl = String.format("http://%s%s", apiWrapperObj.get("service_id"), apiWrapperObj.get("api_path"));
        Map<String, Object> requestParams = uriVariables.length > 0 ? uriVariables[0] : null;
        Map<String, Object> requestHeaders = uriVariables.length > 1 ? uriVariables[1] : null;
        Map<String, Object> requestPostUriParam = uriVariables.length > 2 ? uriVariables[2] : null;

        //处理令牌隐藏header参数传递----------------------------------------------begin
        if (requestHeaders == null) {
            requestHeaders = new HashMap<>(0);
        }
        //封装接入许可
        requestHeaders.put(TokenManager.X_FORWARDED_FOR, AppThreadCache.getXForwardedFor());
        requestHeaders.put(TokenManager.REFERER, AppThreadCache.getReferer());
        requestHeaders.put(TokenManager.APP_ID, AppThreadCache.getAppId());
        requestHeaders.put(TokenManager.APP_KEY, AppThreadCache.getAppKey());
        requestHeaders.put(TokenManager.APP_SECRET, AppThreadCache.getAppSecret());
        requestHeaders.put(TokenManager.APP_SIGNATURE, AppThreadCache.getAppSignature());

        //封装访问许可
        if(!requestHeaders.containsKey(applicationConstant.ssId)) {
            requestHeaders.put(applicationConstant.ssId, AppThreadCache.getSsId());
        }
        requestHeaders.put(applicationConstant.vfId, AppThreadCache.getVfId());
        requestHeaders.put(applicationConstant.scopeId, AppThreadCache.getScopeId() == null ? "1" : AppThreadCache.getScopeId());
        //处理令牌隐藏header参数传递----------------------------------------------end

        Map<String, Object> finalRequestHeaders = requestHeaders;

        HystrixCommand.Setter setter = defaultSetterFor(apiWrapperObj.get("service_id"), api, HttpMethod.resolve(apiWrapperObj.get("api_method")));
        HystrixCommand<T> hystrixCommand = new HystrixCommand<T>(setter) {
            @Override
            protected T run() throws Exception {

                /*报文头处理*/
                HttpHeaders httpHeaders = new HttpHeaders();
                for (Map.Entry<String, Object> entry : finalRequestHeaders.entrySet()) {
                    httpHeaders.add(entry.getKey(), (String) entry.getValue());
                }
                httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

                /*调用处理*/
                if (HttpMethod.GET.matches(apiWrapperObj.get("api_method"))) {
                    HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
                    ResponseEntity<T> results;
                    if (requestParams != null) {
                        results = lbRestTemplate.exchange(serviceUrl, HttpMethod.GET, entity, responseType, requestParams);
                    } else {
                        results = lbRestTemplate.exchange(serviceUrl, HttpMethod.GET, entity, responseType);
                    }
                    return results.getBody();

                } else if (HttpMethod.POST.matches(apiWrapperObj.get("api_method"))) {
                    HttpEntity<?> httpEntity = new HttpEntity<Object>(JSON.toJSONString(requestParams), httpHeaders);
                    ResponseEntity<T> results;
                    if (requestPostUriParam != null) {
                        results = lbRestTemplate.exchange(serviceUrl, HttpMethod.POST, httpEntity, responseType, requestPostUriParam);
                    } else {
                        results = lbRestTemplate.exchange(serviceUrl, HttpMethod.POST, httpEntity, responseType);
                    }
                    return results.getBody();
                } else {
                    throw new RemoteException(String.format("Msp remote service call import http method：[%s]nonsupport", apiWrapperObj.get("api_method")));
                }
            }

            @Override
            protected T getFallback() {
                logger.info(String.format("Msp remote service call hystrix exception REQ：[%s]", apiWrapperObj));
                logger.error("Msp remote service call hystrix exception ERR：", this.getExecutionException());
                throw new RemoteException(String.format("Msp remote service call hystrix exception：[%s]:[%s]", apiWrapperObj.get("service_id"), apiWrapperObj.get("api_path")));
            }
        };
        return hystrixCommand.execute();
    }

    private HystrixCommand.Setter defaultSetterFor(String serviceId, String api, HttpMethod method) {
        return HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RMC:" + serviceId))
                .andCommandKey(HystrixCommandKey.Factory.asKey(serviceId + ":" + api));
    }

    private static class HystrixCommandOptions {

        private final String cacheKey;
        private final HystrixCommand.Setter setter;

        private HystrixCommandOptions(HystrixCommandOptions.Builder builder) {
            this.cacheKey = builder.cacheKey;
            this.setter = builder.setter;
        }

        /**
         * Gets cache key.
         *
         * @return the cache key
         */
        public String getCacheKey() {
            return cacheKey;
        }

        /**
         * Gets setter.
         *
         * @return the setter
         */
        public HystrixCommand.Setter getSetter() {
            return setter;
        }

        /**
         * New builder hystrix command options . builder.
         *
         * @return the hystrix command options . builder
         * @since 2019.06.11
         */
        public static HystrixCommandOptions.Builder newBuilder() {
            return new HystrixCommandOptions.Builder();
        }

        /**
         * The type Builder.
         * Description
         *
         * @param <T> the type parameter
         * @author hujiang.
         * @version 1.0
         * @since 2019.06.11
         */
        static class Builder<T> {
            private String cacheKey = null;
            private HystrixCommand.Setter setter = null;

            /**
             * Sets cache key.
             *
             * @param cacheKey the cache key
             * @return the cache key
             */
            public HystrixCommandOptions.Builder setCacheKey(String cacheKey) {
                this.cacheKey = cacheKey;
                return this;
            }

            /**
             * Sets setter.
             *
             * @param setter the setter
             * @return the setter
             */
            public HystrixCommandOptions.Builder setSetter(HystrixCommand.Setter setter) {
                this.setter = setter;
                return this;
            }

            /**
             * Build hystrix command options.
             *
             * @return the hystrix command options
             * @since 2019.06.11
             */
            public HystrixCommandOptions build() {
                return new HystrixCommandOptions(this);
            }
        }
    }
}

