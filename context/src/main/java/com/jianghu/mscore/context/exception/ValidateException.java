package com.jianghu.mscore.context.exception;

/**
 * The type Validate exception.
 * 校验异常
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public class ValidateException extends RuntimeException {


    /**
     * The type Validate exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public ValidateException(String message) {
        super(message);
    }

    /**
     * The type Validate exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The type Validate exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public ValidateException(Throwable cause) {
        super(cause);
    }

    /**
     * The type Validate exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    protected ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

