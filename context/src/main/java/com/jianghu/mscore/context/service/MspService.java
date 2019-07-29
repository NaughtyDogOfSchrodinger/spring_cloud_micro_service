package com.jianghu.mscore.context.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 封装带事务的 Service 的只读注解 默认超时5s
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Service
@Scope("prototype")
public @interface MspService {
}
