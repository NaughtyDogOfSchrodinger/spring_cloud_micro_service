package com.jianghu.mscore.context.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * restTemplate统一添加请求头
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
@Deprecated
public class HttpRequestInterceptor implements ClientHttpRequestInterceptor {
    /**
     * The Headers.
     */
    private Map<String, ?> headers;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        for (Map.Entry<String, ?> entry : headers.entrySet()) {
            if(httpRequest.getHeaders().containsKey(entry.getKey())){
                httpRequest.getHeaders().set(entry.getKey(), (String) entry.getValue());
            }else{
                httpRequest.getHeaders().add(entry.getKey(), (String) entry.getValue());
            }
        }
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

    /**
     * The type Http request interceptor.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    @Autowired
    public HttpRequestInterceptor(Map<String, ?> headers) {
        if (headers == null) {
            this.headers = new HashMap<>();
        } else {
            this.headers = headers;
        }
    }
}

