package com.jianghu.mscore.web.util;

import java.io.Serializable;

/**
 * Rest结果值基类
 *
 * @param <T> the type parameter
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
public class RmcResultWrapper<T> implements Serializable {
    /**
     * The Msg.
     */
    public String msg;
    /**
     * The Status.
     */
    public Long status;
    /**
     * The Timestamp.
     */
    public Long timestamp;
    /**
     * The Data.
     */
    public T data;

    /**
     * The type Rmc result wrapper.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2018.12.19
     */
    public RmcResultWrapper() {
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Long getStatus() {
        return this.status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return this.data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }
}

