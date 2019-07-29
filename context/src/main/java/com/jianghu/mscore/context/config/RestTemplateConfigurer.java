package com.jianghu.mscore.context.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;


/**
 * RestTemplate配置类
 * {@link com.jianghu.mscore.context.remote.RmcInvoker}中开启负载均衡
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.05.10
 */
@Component
public class RestTemplateConfigurer {
    /**
     * rpc使用服务名称调用开启负载均衡
     *
     * @return the rest template
     * @since 2019.05.10
     */
    @Bean
    @LoadBalanced
    public RestTemplate lbRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    /**
     * rpc使用ip端口号调用关闭负载均衡
     *
     * @return the rest template
     * @since 2019.05.10
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}

