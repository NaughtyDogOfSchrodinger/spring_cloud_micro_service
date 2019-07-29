package com.jianghu.mscore.core.exception;


import com.jianghu.mscore.api.CommonErrorCode;
import com.jianghu.mscore.api.ErrorCode;
import com.jianghu.mscore.exception.BaseException;

/**
 * The type App business exception.
 * 定义业务异常
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.26
 */
public class AppBusinessException extends BaseException {

    private static final ErrorCode DEFAULT_CODE = CommonErrorCode.INTERNAL_ERROR;

    private String code = DEFAULT_CODE.getCode();

    /**
     *  类似Http状态码
     */
    private int httpStatus = DEFAULT_CODE.getStatus();

    /**
     * The type App business exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.26
     */
    public AppBusinessException(String code, int httpStatus, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    /**
     * The type App business exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.26
     */
    public AppBusinessException(String message) {
        super(message);
    }

    /**
     * The type App business exception.
     * Description
     *
     * @param errorCode 状态码, 这个字段会在错误信息里返回给客户端.
     * @param message
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.26
     */
    public AppBusinessException(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), errorCode.getStatus(), message);
    }

    /**
     * The type App business exception.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.26
     */
    public AppBusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets http status.
     *
     * @return the http status
     */
    public int getHttpStatus() {
        return httpStatus;
    }
}

