package com.jianghu.mscore.exception;

/**
 * 自定义异常父类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
public class BaseException extends RuntimeException {

    /**
     * The type Msp exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * The type Msp exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The type Msp exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * The type Msp exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
