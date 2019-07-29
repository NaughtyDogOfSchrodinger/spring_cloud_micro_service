package com.jianghu.mscore.pay.vo;

import com.alibaba.fastjson.JSONObject;

public class PayModel {

    private boolean success;

    private String msg;

    private JSONObject data;

    private String body;

    private PayModel() {
    }

    public PayModel(final boolean success, final JSONObject data) {
        this.success = success;
        this.data = data;
    }

    public PayModel(final boolean success, final String msg) {
        this.success = success;
        this.msg = msg;
    }

    public PayModel(final boolean success, final String msg, final JSONObject data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public PayModel(final boolean success, final String msg, final String body) {
        this.success = success;
        this.msg = msg;
        this.body = body;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(final JSONObject data) {
        this.data = data;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }
}

