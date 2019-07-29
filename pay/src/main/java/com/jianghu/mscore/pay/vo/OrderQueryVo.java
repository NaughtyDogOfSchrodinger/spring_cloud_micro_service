package com.jianghu.mscore.pay.vo;

import java.io.Serializable;

public class OrderQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNum;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
