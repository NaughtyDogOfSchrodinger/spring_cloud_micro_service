package com.jianghu.mscore.context.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 容器holder
 * 获取应用上下文将{@link ApplicationConstant#serviceName}
 * 加入线程变量{@link AppThreadCache}
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    /**
     * The constant context.
     */
    public static ApplicationContext context;

    /**
     * The constant constant.
     */
    private static ApplicationConstant constant;

    /**
     * The constant INSTANCE.
     */
    private static final ApplicationContextHolder INSTANCE = new ApplicationContextHolder();

    private ApplicationContextHolder(){}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ApplicationContextHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        constant = applicationContext.getBean(ApplicationConstant.class);
        AppThreadCache.setServiceName(constant.serviceName);
    }
}

