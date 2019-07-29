package com.jianghu.mscore.exception;

/**
 * 自定义web异常
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
public class WebException extends BaseException {

    /**
     * The type Msp exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public WebException(String message) {
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
    public WebException(String message, Throwable cause) {
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
    public WebException(Throwable cause) {
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
    protected WebException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
