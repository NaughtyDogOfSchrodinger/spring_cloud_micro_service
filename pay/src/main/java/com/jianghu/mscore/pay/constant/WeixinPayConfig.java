package com.jianghu.mscore.pay.constant;

import com.jianghu.mscore.pay.util.IWXPayDomain;
import com.jianghu.mscore.pay.util.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static com.jianghu.mscore.pay.util.WXPayConstants.DOMAIN_API;


@Component
public class WeixinPayConfig extends WXPayConfig {

    @Value("${pay.weChat.appId}")
    private String appId;

    @Value("${pay.weChat.appSecret}")
    private String appSecret;

    @Value("${pay.weChat.mchId}")
    private String mchId;

    @Value("${pay.weChat.wxKey}")
    private String wxKey;

    @Value("${pay.weChat.certPath:classpath:/cert/apiclient_cert.p12}")
    private String certPath;

    @Value("${pay.weChat.callbackUrl}")
    private String callbackUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getWxKey() {
        return wxKey;
    }

    public void setWxKey(String wxKey) {
        this.wxKey = wxKey;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Override
    protected InputStream getCertStream() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(certPath).getInputStream();
    }

    @Override
    protected IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
                System.out.println(domain);
            }

            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(DOMAIN_API, true);
            }
        };
    }
}
