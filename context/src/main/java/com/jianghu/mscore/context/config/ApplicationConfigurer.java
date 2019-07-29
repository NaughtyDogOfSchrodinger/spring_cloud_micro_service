package com.jianghu.mscore.context.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * spring配置类
 * {@link ConfigurationProperties}将配置文件信息读入该实体类
 * {@link EnableAutoConfiguration}将{@link ApplicationConfigurer}加入ioc容器
 * {@link EnableConfigurationProperties}注解的beans将自动被Environment属性配置
 *
 * @author hujiang.
 * @version 1.0
 * @date 2017 /6/10
 * @since 2019.04.24
 */
@Configuration
@ConfigurationProperties
@EnableAutoConfiguration
@EnableConfigurationProperties
public class ApplicationConfigurer {

    /**
     * The Spring.
     */
    private Map<String, Object> spring = new HashMap<String, Object>();

    public Map<String, Object> getSpring() {
        return spring;
    }

    public void setSpring(Map<String, Object> spring) {
        this.spring = spring;
    }
}

