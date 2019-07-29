package com.jianghu.mscore.context.exception;

import com.jianghu.mscore.exception.MspException;

/**
 * The type Base exception.
 * 异常基类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public class RemoteException extends MspException {

    /**
     * The type Remote exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public RemoteException(String message) {
        super(message);
    }

    /**
     * The type Remote exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public RemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The type Remote exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    public RemoteException(Throwable cause) {
        super(cause);
    }

    /**
     * The type Remote exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.24
     */
    protected RemoteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

