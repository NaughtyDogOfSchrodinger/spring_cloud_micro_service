package com.jianghu.mscore.web.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * api controller使用注解，单例，返回json数据
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@RestController
@RequestMapping
public @interface ApiController {

    /**
     * url前缀
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] value() default {"/api"};

    /**
     * 路径前缀
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] path() default {"/api"};

    /**
     * 请求方法
     *
     * @return the request method [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    RequestMethod[] method() default {};

    /**
     * 请求参数
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] params() default {};

    /**
     * 请求头
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] headers() default {};

    /**
     * 处理数据类型
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] consumes() default {};

    /**
     * 产生数据类型
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] produces() default {};
}

