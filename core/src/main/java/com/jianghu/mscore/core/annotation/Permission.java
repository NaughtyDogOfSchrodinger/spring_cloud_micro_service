package com.jianghu.mscore.core.annotation;

import com.jianghu.mscore.core.mvc.web.controller.AbstractController;

import java.lang.annotation.*;

/**
 * 权限生成注解
 * {@link AbstractController#generateAuthority(String)}
 *  无该注解修饰类不扫描
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Permission {

    /**
     * 默认该注解修饰类为选项卡
     *
     * @return the boolean
     * @since 2019.06.11
     */
    boolean isSection() default true;

    /**
     * 菜单展示顺序
     *
     * @return the int
     * @since 2019.06.11
     */
    int showOrder() default -1;


}

