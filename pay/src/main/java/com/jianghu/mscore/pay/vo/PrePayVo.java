package com.jianghu.mscore.pay.vo;

public class PrePayVo {

    private String deviceInfo;

    private String totalFee;

    private String spBillCreateIp;

    private String body;

    public PrePayVo(String totalFee, String body) {
        this.totalFee = totalFee;
        this.body = body;
    }

    public PrePayVo(String deviceInfo, String totalFee, String spBillCreateIp, String body) {
        this.deviceInfo = deviceInfo;
        this.totalFee = totalFee;
        this.spBillCreateIp = spBillCreateIp;
        this.body = body;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpBillCreateIp() {
        return spBillCreateIp;
    }

    public void setSpBillCreateIp(String spBillCreateIp) {
        this.spBillCreateIp = spBillCreateIp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
