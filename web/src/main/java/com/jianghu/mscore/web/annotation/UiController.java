package com.jianghu.mscore.web.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 页面交互controller注解，单例
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Controller
@RequestMapping
public @interface UiController {

    /**
     * Value string [ ].
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] value() default {"/ui"};

    /**
     * Path string [ ].
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] path() default {"/ui"};

    /**
     * Method request method [ ].
     *
     * @return the request method [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    RequestMethod[] method() default {};

    /**
     * Params string [ ].
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] params() default {};

    /**
     * Headers string [ ].
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
     * 返回数据类型
     *
     * @return the string [ ]
     * @since 2018.12.19
     */
    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] produces() default {};
}

