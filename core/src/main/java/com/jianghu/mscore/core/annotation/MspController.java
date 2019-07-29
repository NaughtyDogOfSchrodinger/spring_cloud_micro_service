package com.jianghu.mscore.core.annotation;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * <p>
 * 该注解封装了 RestController 和 RequestMapping
 * <p>
 * 添加默认配置前缀 "/api" 用于远程的API调用
 * <p>
 * 配置可更改在@MspController(自定义)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@RestController
@RequestMapping
@Scope("prototype")
public @interface MspController {
    @AliasFor(annotation = RequestMapping.class)  String[] value() default {"/api"};

    @AliasFor(annotation = RequestMapping.class) String[] path() default {"/api"};

    @AliasFor(annotation = RequestMapping.class) RequestMethod[] method() default {};

    @AliasFor(annotation = RequestMapping.class) String[] params() default {};

    @AliasFor(annotation = RequestMapping.class) String[] headers() default {};

    @AliasFor(annotation = RequestMapping.class) String[] consumes() default {};

    @AliasFor(annotation = RequestMapping.class) String[] produces() default {};

}


