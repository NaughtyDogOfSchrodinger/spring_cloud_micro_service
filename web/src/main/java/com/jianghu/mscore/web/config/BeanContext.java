package com.jianghu.mscore.web.config;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ioc中取bean，多线程使用
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.29
 */
@Component
public class BeanContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanContext.applicationContext = applicationContext;
    }

    /**
     * 获取上下文
     *
     * @return the application context
     * @since 2018.12.29
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取bean
     *
     * @param <T>  the type parameter
     * @param name the name
     * @return the bean
     * @throws BeansException the beans exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T)applicationContext.getBean(name);
    }

    /**
     * 获取bean
     *
     * @param <T> the type parameter
     * @param clz the clz
     * @return the bean
     * @throws BeansException the beans exception
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return applicationContext.getBean(clz);
    }

}
