package com.jianghu.mscore.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(
        method = {RequestMethod.POST}
)
public @interface MspPostMapping {
    @AliasFor(annotation = RequestMapping.class) String[] value() default {""};

    @AliasFor(annotation = RequestMapping.class) String[] path() default {""};

    @AliasFor(annotation = RequestMapping.class) String[] params() default {};

    @AliasFor(annotation = RequestMapping.class) String[] headers() default {};

    @AliasFor(annotation = RequestMapping.class) String[] consumes() default {
            MediaType.APPLICATION_JSON_UTF8_VALUE
    };

    @AliasFor(annotation = RequestMapping.class) String[] produces() default {
            MediaType.APPLICATION_JSON_UTF8_VALUE
    };
}
