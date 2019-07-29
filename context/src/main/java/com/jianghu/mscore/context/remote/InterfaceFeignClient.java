package com.jianghu.mscore.context.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The interface Interface feign client.
 * Description
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public interface InterfaceFeignClient {
    /**
     * interface of FeignClient
     *
     * @return logger logger
     */
    default Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}

