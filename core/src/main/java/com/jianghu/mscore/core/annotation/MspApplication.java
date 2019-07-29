package com.jianghu.mscore.core.annotation;

import com.jianghu.mscore.core.config.BaseConfiguration;
import com.jianghu.mscore.core.context.WebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.*;

/**
 * 整合spring boot spring cloud 相关接口, 整合基础配置
 *
 * 将{@link WebApplication}和{@link BaseConfiguration}加入IOC容器
 * 开启异步任务调度{@link EnableAsync} {@link EnableScheduling}
 * 开启断路器功能{@link EnableCircuitBreaker} 注册至注册中心{@link EnableEurekaClient}
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableCircuitBreaker
@EnableEurekaClient
@EnableFeignClients
@Import({BaseConfiguration.class, WebApplication.class})
public @interface MspApplication {

}
