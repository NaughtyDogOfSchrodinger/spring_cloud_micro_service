package com.jianghu.mscore.web.config;

import com.jianghu.mscore.web.controller.MyErrorController;
import com.jianghu.mscore.web.interceptor.GlobalInterceptor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@ComponentScan(basePackages = {"com.jianghu.**.web","com.jianghu.**.service"
        ,"com.jianghu.**.config","com.**"}, excludeFilters={
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= MyErrorController.class)})
public class BaseConfigurer extends WebMvcConfigurationSupport {

    @Resource
    private GlobalInterceptor globalInterceptor;

    @Bean
    public MyErrorController errorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        return new MyErrorController(errorAttributes, serverProperties.getError());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET, POST, PUT, DELETE, OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options")
                .allowCredentials(true).maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor).addPathPatterns("/**");
    }
}

