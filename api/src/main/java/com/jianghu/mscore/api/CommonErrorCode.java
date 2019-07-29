package com.jianghu.mscore.api;

/**
 * api接口请求 通用错误码
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.23
 */
public enum CommonErrorCode implements ErrorCode {

    /**
     * Bad request common error code.
     */
    BAD_REQUEST(400, "请求的参数个数或格式不符合要求"),
    /**
     * Invalid argument common error code.
     */
    INVALID_ARGUMENT(400, "请求的参数不正确"),
    /**
     * Unauthorized common error code.
     */
    UNAUTHORIZED(401, "无权访问"),
    /**
     * Forbidden common error code.
     */
    FORBIDDEN(403, "禁止访问"),
    /**
     * Not found common error code.
     */
    NOT_FOUND(404, "请求的地址不正确"),
    /**
     * Method not allowed common error code.
     */
    METHOD_NOT_ALLOWED(405, "不允许的请求方法"),
    /**
     * Not acceptable common error code.
     */
    NOT_ACCEPTABLE(406, "不接受的请求"),
    /**
     * Conflict common error code.
     */
    CONFLICT(409, "资源冲突"),
    /**
     * The Unsupported media type.
     */
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的Media Type"),
    /**
     * Internal error common error code.
     */
    INTERNAL_ERROR(500, "服务器内部错误"),
    /**
     * Service unavailable common error code.
     */
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    /**
     * Gateway timeout common error code.
     */
    GATEWAY_TIMEOUT(504, "请求服务超时");

    private int status;

    private String message;

    CommonErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * 根据http状态获取请求枚举
     *
     * @param httpStatus the http status
     * @return common error code
     * @since 2019.04.23
     */
    public static CommonErrorCode fromHttpStatus(int httpStatus) {
        for(CommonErrorCode errorCode : values()) {
            if(errorCode.getStatus() == httpStatus) {
                return errorCode;
            }
        }
        return INTERNAL_ERROR;
    }


    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

