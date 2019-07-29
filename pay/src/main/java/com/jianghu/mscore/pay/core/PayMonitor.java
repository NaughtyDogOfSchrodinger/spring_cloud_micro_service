package com.jianghu.mscore.pay.core;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jianghu.mscore.file.util.ZXingCode;
import com.jianghu.mscore.pay.callback.OrderProcess;
import com.jianghu.mscore.pay.constant.AliPay;
import com.jianghu.mscore.pay.constant.WeixinPayConfig;
import com.jianghu.mscore.pay.exception.PayException;
import com.jianghu.mscore.pay.util.WXPay;
import com.jianghu.mscore.pay.util.WXPayConstants;
import com.jianghu.mscore.pay.vo.OrderQueryVo;
import com.jianghu.mscore.pay.vo.PayModel;
import com.jianghu.mscore.pay.vo.PrePayVo;
import com.jianghu.mscore.pay.vo.WxCallbackVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PayMonitor {

    private static ConcurrentHashMap<String, String> qrCodeMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Resource
    private WXPay wxPay;

    @Resource
    private WeixinPayConfig weixinPayConfig;

    private Map<String, String> constructPrePayMap(PrePayVo prePayVo) {
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", UUID.randomUUID().toString().replace("-", ""));
        data.put("device_info", prePayVo.getDeviceInfo());
        data.put("fee_type", "CNY");
        data.put("total_fee", prePayVo.getTotalFee());
        data.put("spbill_create_ip", prePayVo.getSpBillCreateIp());
        data.put("notify_url", weixinPayConfig.getCallbackUrl());
        data.put("trade_type", "NATIVE");
        data.put("body", prePayVo.getBody());
        return data;
    }

    public String prePay(OrderProcess orderProcess, PrePayVo prePayVo) throws Exception {
        Map<String, String> prePayMap = constructPrePayMap(prePayVo);
        Map<String, String> result = wxPay.unifiedOrder(prePayMap);
        if (!Objects.equals(result.get("return_code"), WXPayConstants.SUCCESS) || !Objects.equals(result.get("result_code"), WXPayConstants.SUCCESS)) {
            throw new PayException(result.get("err_code_des"));
        }
        orderProcess.process(prePayMap, prePayVo);
        String orderNumber = prePayMap.get("out_trade_no");
        qrCodeMap.put(orderNumber, result.get("code_url"));
        return orderNumber;
    }

    public String getQrCode(String orderNum) {
        return ZXingCode.getLogoQRCode(qrCodeMap.get(orderNum));
    }


    public void orderQuery(OrderProcess orderProcess, OrderQueryVo orderQueryVo) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", orderQueryVo.getOrderNum());
        Map<String, String> resp = wxPay.orderQuery(data);
        WxCallbackVo callbackVo = new WxCallbackVo();
//        BeanUtil.transMap2Bean(resp, callbackVo);
        if (!Objects.equals(callbackVo.getReturn_code(), WXPayConstants.SUCCESS)
                || !Objects.equals(callbackVo.getResult_code(), WXPayConstants.SUCCESS)
                || !Objects.equals(callbackVo.getTrade_state(), WXPayConstants.SUCCESS)) {
            throw new PayException("交易状态：" + callbackVo.getTrade_state() +
                    "交易状态描述：" + callbackVo.getTrade_state_desc());
        }
        orderProcess.process(resp, null);
    }

    @Autowired
    private AliPay aliPay;

    private PayModel requestAliPay(PrePayVo prePayVo) throws AlipayApiException {
        //写入业务逻辑，返回订单信息
        Map<String, String> content = new HashMap<>(4);
        //订单号
        content.put("out_trade_no", UUID.randomUUID().toString().replace("-", ""));
        //总金额
        content.put("total_amount", prePayVo.getTotalFee());
        //商品标题
        content.put("subject", prePayVo.getBody());
        content.put("product_code", "FAST_INSTANT_TRADE_PAY");
        PayModel aliPayModel = aliPay.pagePay(content);
        if (!aliPayModel.isSuccess()) {
            throw new PayException(String.format("调用支付出错:%s", aliPayModel.getMsg()));
        }
        return aliPayModel;
    }


    public void aliPay(OrderProcess orderProcess, PrePayVo prePayVo, HttpServletResponse res)  {
        try {
            //保存订单
            final PayModel aliPayModel = requestAliPay(prePayVo);
            orderProcess.process(new HashMap<>(), prePayVo);
            res.setContentType("text/html;charset=utf-8");
            //直接将完整的表单html输出到页面
            res.getWriter().write(aliPayModel.getBody());
            res.getWriter().flush();
            res.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String aliPayNotify(HttpServletRequest request, OrderProcess orderProcess) {
        String  message = "success";
        Map<String, String> params = new HashMap<>();
        // 取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        //验证签名 校验签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, aliPay.getAliPayPublicKey(), aliPay.getCharset(), aliPay.getSignType());
            //各位同学这里可能需要注意一下,2018/01/26 以后新建应用只支持RSA2签名方式，目前已使用RSA签名方式的应用仍然可以正常调用接口，注意下自己生成密钥的签名算法
            //signVerified = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), "UTF-8","RSA2");
            //有些同学通过 可能使用了这个API导致验签失败，特此说明一下
            //signVerified = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "UTF-8");//正式环境
        } catch (AlipayApiException | IOException e) {
            e.printStackTrace();
            message =  "failed";
        }
        if (signVerified) {
            logger.info("支付宝验证签名成功！");
            // 若参数中的appid和填入的appid不相同，则为异常通知
            if (!aliPay.getAppId().equals(params.get("app_id"))) {
                logger.info("与付款时的appid不同，此为异常通知，应忽略！");
                message =  "failed";
            }else{
                String outtradeno = params.get("out_trade_no");
                //在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
                String status = params.get("trade_status");
                switch (status) {
                    case "WAIT_BUYER_PAY":  // 如果状态是正在等待用户付款
                        logger.info(outtradeno + "订单的状态正在等待用户付款");
                        break;
                    case "TRADE_CLOSED":  // 如果状态是未付款交易超时关闭，或支付完成后全额退款
                        logger.info(outtradeno + "订单的状态已经关闭");
                        break;
                    case "TRADE_SUCCESS":
                        params.forEach((key, value) -> logger.info(key + ":" + value));
                        orderProcess.process(null, null);
                    case "TRADE_FINISHED":  // 如果状态是已经支付成功
                        logger.info("(支付宝订单号:" + outtradeno + "付款成功)");
                        //这里 根据实际业务场景 做相应的操作
                        break;
                    default:
                        break;
                }
            }
        } else { // 如果验证签名没有通过
            message =  "failed";
            logger.info("验证签名失败！");
        }
        return message;
    }




}

