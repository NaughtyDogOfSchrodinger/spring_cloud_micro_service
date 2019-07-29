package com.jianghu.mscore.context.exception;

/**
 * The type Base exception.
 * 异常基类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public class ServiceException extends RuntimeException {

    /**
     * The type Service exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * The type Service exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The type Service exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * The type Service exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
