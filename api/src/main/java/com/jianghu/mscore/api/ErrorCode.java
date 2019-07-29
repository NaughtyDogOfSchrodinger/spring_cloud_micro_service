package com.jianghu.mscore.api;

/**
 * 统一错误码接口
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.23
 */
public interface ErrorCode {

    /**
     * Gets code.
     *
     * @return the code
     */
    String getCode();

    /**
     * Gets status.
     *
     * @return the status
     */
    int getStatus();

    /**
     * Gets message.
     *
     * @return the message
     */
    String getMessage();
}

