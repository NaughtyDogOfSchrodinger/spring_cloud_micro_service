package com.jianghu.mscore.pay.util;

import java.io.IOException;
import java.io.InputStream;

public abstract class WXPayConfig {

    /**
     * 获取 App ID
     *
     * @return App ID
     */
    protected abstract String getAppId();


    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    protected abstract String getMchId();


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    protected abstract String getWxKey();


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    protected abstract InputStream getCertStream() throws IOException;

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    protected int getHttpConnectTimeoutMs() {
        return 6*1000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    protected int getHttpReadTimeoutMs() {
        return 8*1000;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     * @return
     */
    protected abstract IWXPayDomain getWXPayDomain();

    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    protected boolean shouldAutoReport() {
        return true;
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    protected int getReportWorkerNum() {
        return 6;
    }


    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    protected int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    protected int getReportBatchSize() {
        return 10;
    }

}