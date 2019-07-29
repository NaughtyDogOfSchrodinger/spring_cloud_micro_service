package com.jianghu.mscore.core.config;

import com.jianghu.mscore.context.config.ApplicationConstant;
import com.jianghu.mscore.context.config.ApplicationContextHolder;
import com.jianghu.mscore.core.mvc.web.controller.AppErrorController;
import com.jianghu.mscore.core.mvc.web.controller.AppExceptionHandlerController;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * The type Base configuration.
 * 定义Spring cloud基础支持的配置
 * 定义扫包范围
 * 定义容器常量存储
 * {@link com.jianghu.mscore.core.annotation.MspApplication}中加入ioc容器
 * 定义扫包范围，排除指定{@link AppErrorController}
 * 和{@link AppExceptionHandlerController}两个bean
 * 并在{@link com.jianghu.mscore.core.context.WebApplication}中加入ioc容器
 * 将{@link com.jianghu.mscore.context.config.ApplicationConstant}
 * 和{@link com.jianghu.mscore.context.config.ApplicationContextHolder}
 * 和{@link org.springframework.validation.beanvalidation.MethodValidationPostProcessor}
 * 加入ioc容器
 * @author hujiang.
 * @version 1.0
 * @since 2019.05.31
 */
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages ={"com.jianghu.**.service","com.jianghu.**.remote"})
@ComponentScan(basePackages = {"com.jianghu.**.web","com.jianghu.**.service"
        ,"com.jianghu.**.config","com.jianghu.**.mapper"
        ,"com.jianghu.**.action","com.jianghu.**.manager"
        ,"com.jianghu.**.remote","com.jianghu.**"}, excludeFilters={
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value = {AppErrorController.class, AppExceptionHandlerController.class})})
//                , DruidDataSourceAutoConfigure.class})})
public class BaseConfiguration {

    /**
     * Application constant application constant.
     *
     * @return the application constant
     * @since 2019.05.31
     */
    @Bean
    public ApplicationConstant applicationConstant() {
        return new ApplicationConstant();
    }

    /**
     * Application context holder application context holder.
     *
     * @return the application context holder
     * @since 2019.05.31
     */
    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return ApplicationContextHolder.getInstance();
    }

    /**
     * Method validation post processor method validation post processor.
     *
     * @return the method validation post processor
     * @since 2019.05.31
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
