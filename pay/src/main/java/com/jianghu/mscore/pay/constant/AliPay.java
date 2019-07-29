package com.jianghu.mscore.pay.constant;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.jianghu.mscore.pay.vo.PayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class AliPay extends AliPayConfig{


    @Autowired
    private AlipayClient alipayClient;

    @Bean
    public AlipayClient alipayClient() throws IOException {
        return new DefaultAlipayClient(getGatewayUrl(), getAppId(), getMerchantPrivateKey(), "json", getCharset(), getAliPayPublicKey(), getSignType());
    }

    /**
     * 支付
     *
     * @return
     * @throws AlipayApiException
     */
    public PayModel pay(Map<String, String> content)
            throws AlipayApiException {
        return pay(getReturnUrl(), getNotifyUrl(), content);
    }

    /**
     * 支付
     *
     * @return
     * @throws AlipayApiException
     */
    public PayModel pay(String returnUrl, String notifyUrl, Map<String, String> content)
            throws AlipayApiException {
        AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();
        //设置业务参数
        alipayRequest.setBizContent(JSONObject.toJSONString(content));
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setReturnUrl(returnUrl);
        AlipayTradePrecreateResponse response = alipayClient.execute(alipayRequest);
        return new PayModel(response.isSuccess(), response.getMsg(),
                JSONObject.parseObject(response.getBody()));
    }

    /**
     * 支付
     *
     * @return
     * @throws AlipayApiException
     */
    public PayModel pagePay(Map<String, String> content)
            throws AlipayApiException {
        return pagePay(getReturnUrl(), getNotifyUrl(), content);
    }

    /**
     * 支付
     *
     * @return
     * @throws AlipayApiException
     */
    public PayModel pagePay(String returnUrl, String notifyUrl, Map<String, String> content)
            throws AlipayApiException {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //设置业务参数
        final String bizContent = JSONObject.toJSONString(content);
        alipayRequest.setBizContent(bizContent);
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setReturnUrl(returnUrl);
        final AlipayTradePagePayResponse response = alipayClient
                .pageExecute(alipayRequest);
        return new PayModel(response.isSuccess(), response.getMsg(),
                response.getBody());
    }





}
