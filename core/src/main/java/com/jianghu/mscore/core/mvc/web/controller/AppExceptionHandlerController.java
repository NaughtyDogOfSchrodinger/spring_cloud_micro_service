package com.jianghu.mscore.core.mvc.web.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.jianghu.mscore.api.CommonErrorCode;
import com.jianghu.mscore.api.ErrorCode;
import com.jianghu.mscore.api.Error;
import com.jianghu.mscore.context.exception.RemoteException;
import com.jianghu.mscore.core.exception.AppBusinessException;
import com.jianghu.mscore.core.exception.RemoteCallException;
import com.jianghu.mscore.util.JsonUtils;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.03
 */
@ControllerAdvice
public class AppExceptionHandlerController extends ResponseEntityExceptionHandler {

    /**
     * The Logger.
     */
    protected Logger logger = LoggerFactory.getLogger(AppExceptionHandlerController.class);


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        logger.error("spring mvc no handler found: " + ex.getMessage(), ex);
        ErrorCode errorCode = CommonErrorCode.fromHttpStatus(status.value());
        return createResponseEntity(errorCode, request.getDescription(false), errorCode.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {

        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        List<ObjectError> allErrors = ex.getAllErrors();
        String errorMessage = extractErrorMessageFromObjectErrors(allErrors, errorCode.getMessage());
        return createResponseEntity(errorCode, request.getDescription(false), errorMessage);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        String errorMessage = extractErrorMessageFromObjectErrors(allErrors, errorCode.getMessage());
        return createResponseEntity(errorCode, request.getDescription(false), errorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {
        logger.error("spring mvc 异常: " + ex.getMessage(), ex);
        ErrorCode errorCode = CommonErrorCode.fromHttpStatus(status.value());
        return createResponseEntity(errorCode, request.getDescription(false), errorCode.getMessage());
    }

    /**
     * Handle remote call exception response entity.
     *
     * @param request the request
     * @param e       the e
     * @return the response entity
     * @since 2019.06.03
     */
    @ExceptionHandler(value = {ServiceUnavailableException.class, RemoteCallException.class})
    public ResponseEntity<Object> handleRemoteCallException(HttpServletRequest request, AppBusinessException e) {

        logger.error(e.getMessage(), e);
        return createResponseEntity(e.getCode(), e.getHttpStatus(), request.getRequestURI(), e.getMessage());
    }

    /**
     * Handle hystrix timeout exception response entity.
     *
     * @param request the request
     * @param e       the e
     * @return the response entity
     * @since 2019.06.03
     */
    @ExceptionHandler(value = HystrixTimeoutException.class)
    public ResponseEntity<Object> handleHystrixTimeoutException(HttpServletRequest request, HystrixTimeoutException e) {
        logger.error(e.getMessage(), e);
        ErrorCode errorCode = CommonErrorCode.GATEWAY_TIMEOUT;
        return createResponseEntity(errorCode, request.getRequestURI(), e.getMessage());
    }

    /**
     * Handle hystrix runtime exception response entity.
     *
     * @param request the request
     * @param e       the e
     * @return the response entity
     * @since 2019.06.03
     */
    @ExceptionHandler(value = {HystrixRuntimeException.class, RemoteException.class})
    public ResponseEntity<Object> handleHystrixRuntimeException(HttpServletRequest request, HystrixRuntimeException e) {
        logger.error("Hystrix Command 运行报错: " + e.getMessage(), e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_ERROR;
        return createResponseEntity(errorCode, request.getRequestURI(), e.getMessage());
    }


    /**
     * Handle app business exception response entity.
     *
     * @param request the request
     * @param e       the e
     * @return the response entity
     * @since 2019.06.03
     */
    @ExceptionHandler(value = AppBusinessException.class)
    public ResponseEntity<Object> handleAppBusinessException(HttpServletRequest request, AppBusinessException e) {
        //业务异常
        return createResponseEntity(e.getCode(), e.getHttpStatus(), request.getRequestURI(), e.getMessage());
    }

    /**
     * Handle exception response entity.
     *
     * @param request the request
     * @param e       the e
     * @return the response entity
     * @since 2019.06.03
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception e) {
        logger.error("服务器发生错误: " + e.getMessage(), e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_ERROR;
        return createResponseEntity(errorCode, request.getRequestURI(), errorCode.getMessage());

    }


    private ResponseEntity<Object> createResponseEntity(ErrorCode errorCode, String requestUri, String message) {
        return createResponseEntity(errorCode.getCode(), errorCode.getStatus(), requestUri, message);
    }

    private ResponseEntity<Object> createResponseEntity(String code, int httpStatus, String requestUri, String message) {
        Error error = new Error(code, requestUri, message);
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("error", error);
        ret.put("msg", error.getMessage());
        String json = JsonUtils.object2Json(ret);
        return ResponseEntity.status(HttpStatus.valueOf(httpStatus)).body(json);

    }


    private String extractErrorMessageFromObjectErrors(List<ObjectError> allErrors, String defaultMessage) {
        if (allErrors == null || allErrors.isEmpty()) {
            return defaultMessage;
        } else {
            List<String> errorMessages = allErrors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return Joiner.on(",").skipNulls().join(errorMessages);
        }
    }

}
