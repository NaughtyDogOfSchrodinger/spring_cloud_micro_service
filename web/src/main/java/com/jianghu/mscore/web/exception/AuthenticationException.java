package com.jianghu.mscore.web.exception;

import com.jianghu.mscore.exception.WebException;

/**
 * 自定义鉴权异常
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
public class AuthenticationException extends WebException {

    /**
     * The type Authentication exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2018.12.19
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * The type Authentication exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2018.12.19
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The type Authentication exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2018.12.19
     */
    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    /**
     * The type Authentication exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2018.12.19
     */
    protected AuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
