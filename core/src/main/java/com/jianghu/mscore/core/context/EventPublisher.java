package com.jianghu.mscore.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * spring事件发布器
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.26
 */
@Component
public class EventPublisher {
    @Resource
    private ApplicationContext applicationContext;

    /**
     * Publish.
     *
     * @param event the event
     * @since 2019.04.26
     */
    public void publish(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}
