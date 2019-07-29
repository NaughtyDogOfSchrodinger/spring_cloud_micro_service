package com.jianghu.mscore.web.config;


import com.jianghu.mscore.web.constant.PlatformEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程变量
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
public class AppThreadCache {

    private static final ThreadLocal<ThreadContext> CACHE = ThreadLocal.withInitial(ThreadContext::new);


    /**
     * 设定平台
     *
     * @param platform the platform
     */
    public static void setPlatform(PlatformEnum platform) {
        CACHE.get().platform = platform;
    }

    /**
     * 获取平台
     *
     * @return the platform
     */
    public static PlatformEnum getPlatform() {
        return CACHE.get().platform;
    }

    /**
     * 设定其他数据
     *
     */
    public static void setExtraData(ConcurrentHashMap<String, Object> map) {
        CACHE.get().extraData = map;
    }

    /**
     * 获取其他数据
     *
     * @param key the key
     * @return the extra data
     */
    public static Object getExtraData(String key) {
        return CACHE.get().extraData.get(key);
    }

    /**
     * 请求头加入信息
     *
     * @param key  the key
     * @param data the data
     * @since 2018.12.19
     */
    public static void putRequestHeader(String key, String data) {
        CACHE.get().requestHeader.put(key, data);
    }

    /**
     * 获取请求头信息
     *
     * @param key the key
     * @return the request header
     */
    public static String getRequestHeader(String key) {
        return (String)CACHE.get().requestHeader.get(key);
    }

    /**
     * 设定开始时间
     */
    public static void setStartTime() {
        CACHE.get().startTime = System.currentTimeMillis();
    }

    /**
     * 获取开始时间
     *
     * @return the start time
     */
    public static long getStartTime() {
        return CACHE.get().startTime;
    }


    /**
     * 获取 session id.
     *
     * @return the session id
     */
    public static String getSessionId() {
        return CACHE.get().sessionId;
    }

    /**
     * 设定 session id.
     *
     * @param sessionId the session id
     */
    public static void setSessionId(String sessionId) {
        CACHE.get().sessionId = sessionId;
    }

    /**
     * 获取 x forwarded for.
     *
     * @return the x forwarded for
     */
    public static String getXForwardedFor() {
        return CACHE.get().xForwardedFor;
    }

    /**
     * 设定 x forwarded for.
     *
     * @param rmtAddr the rmt addr
     */
    public static void setXForwardedFor(String rmtAddr) {
        CACHE.get().xForwardedFor = rmtAddr;
    }


    public static ConcurrentHashMap getExtraData() {
        return CACHE.get().extraData;
    }

    public static void remove() {
        CACHE.remove();
    }


    private static class ThreadContext {
        /**
         * The Platform.
         */
        PlatformEnum platform;
        /**
         * The X forwarded for.
         */
        String xForwardedFor;
        /**
         * The Request header.
         */
        ConcurrentHashMap requestHeader;
        /**
         * The Session id.
         */
        String sessionId;
        /**
         * The Start time.
         */
        long startTime;
        /**
         * The Extra data.
         */
        ConcurrentHashMap<String, Object> extraData;

        private ThreadContext() {
            this.requestHeader = new ConcurrentHashMap();
            this.extraData = new ConcurrentHashMap();
        }
    }
}
