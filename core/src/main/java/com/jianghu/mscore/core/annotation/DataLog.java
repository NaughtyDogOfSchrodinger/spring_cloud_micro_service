package com.jianghu.mscore.core.annotation;

import java.lang.annotation.*;

/**
 * The interface Data log.
 * Description
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.26
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Deprecated
public @interface DataLog {

    /**
     * 字段的名称.也可以当作是字段的解释
     *
     * @return string
     * @since 2019.04.26
     */
    String name();

}

