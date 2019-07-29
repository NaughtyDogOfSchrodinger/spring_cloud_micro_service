package com.jianghu.mscore.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * api 错误类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.23
 */
public class Error {

    private String code;

    private String message;

    private String requestUri;

    /**
     * json构造
     *
     * @param code
     * @param requestUri
     * @param message
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.23
     */
    @JsonCreator
    public Error(@JsonProperty("code") String code,
                 @JsonProperty("requestUri") String requestUri,
                 @JsonProperty(value = "message", defaultValue = "") String message) {
        this.code = code;
        this.requestUri = requestUri;
        this.message = message;
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
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets request uri.
     *
     * @return the request uri
     */
    public String getRequestUri() {
        return requestUri;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", requestUri='" + requestUri + '\'' +
                '}';
    }
}

