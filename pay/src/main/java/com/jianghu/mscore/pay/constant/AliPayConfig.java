package com.jianghu.mscore.pay.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class AliPayConfig {

    @Value("${pay.ali.gatewayUrl:https://openapi.alipay.com/gateway.do}")
    private String gatewayUrl;

    @Value("${pay.ali.appId}")
    private String appId;

    @Value("${pay.ali.merchantPrivateKey:/merchantPrivateKey.txt}")
    private String merchantPrivateKey;

    @Value("${pay.ali.aliPayPublicKey:/aliPayPublicKey.txt}")
    private String aliPayPublicKey;

    @Value("${pay.ali.returnUrl}")
    private String returnUrl;

    @Value("${pay.ali.notifyUrl}")
    private String notifyUrl;

    @Value("${pay.ali.signType:RSA2}")
    private String signType;

    @Value("${pay.ali.charset:utf-8}")
    private String charset;

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getMerchantPrivateKey() throws IOException {
        return getResourceString(merchantPrivateKey);
    }

    private String getResourceString(String resource) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(resource);
        InputStream in = classPathResource.getInputStream();
        byte[] b = new byte[8192];
        int len = 0;
        int temp;          //所有读取的内容都使用temp接收
        while ((temp = in.read()) != -1) {    //当没有读取完时，继续读取
            b[len] = (byte) temp;
            len++;
        }
        in.close();
        return new String(b, 0, len);
    }

    public String getAliPayPublicKey() throws IOException {
        return getResourceString(aliPayPublicKey);
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getSignType() {
        return signType;
    }

    public String getCharset() {
        return charset;
    }


}
