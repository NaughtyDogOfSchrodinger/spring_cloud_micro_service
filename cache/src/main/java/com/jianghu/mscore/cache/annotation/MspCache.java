package com.jianghu.mscore.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MspCache {
    int keepSecond() default 86400;

    String cacheKey() default "";

    boolean reCache() default false;
}
