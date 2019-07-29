package com.jianghu.mscore.core.exception;

import com.jianghu.mscore.api.CommonErrorCode;
import com.jianghu.mscore.api.ErrorCode;

/**
 * The type Service unavailable exception.
 * 服务不可用异常
 *
 * @author liww@hearglobal.com.
 * @version 1.0
 * @since 2017.03.27
 */
public class ServiceUnavailableException extends AppBusinessException {

    private static final ErrorCode ERROR_CODE = CommonErrorCode.SERVICE_UNAVAILABLE;

    public ServiceUnavailableException(String message) {
        super(ERROR_CODE.getCode(), ERROR_CODE.getStatus(), " 远程服务不可用: " + message);
    }

}

