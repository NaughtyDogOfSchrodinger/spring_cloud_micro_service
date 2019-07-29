package com.jianghu.mscore.exception;

/**
 * 自定义微服务异常
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public class MspException extends BaseException {

    /**
     * The type Msp exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public MspException(String message) {
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
    public MspException(String message, Throwable cause) {
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
    public MspException(Throwable cause) {
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
    protected MspException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}


