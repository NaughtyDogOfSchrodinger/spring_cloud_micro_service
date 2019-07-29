package com.jianghu.mscore.context.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * ThreadLocal 存储当前登录信息等状态
 * {@link ThreadLocal#withInitial(Supplier)}
 * {@code
 * public class TestSupplier {
 * 	private int age;
 *
 * 	TestSupplier(){
 * 		System.out.println(age);
 *        }
 * 	public static void main(String[] args) {
 * 		//创建Supplier容器，声明为TestSupplier类型，此时并不会调用对象的构造方法，即不会创建对象
 * 		Supplier<TestSupplier> sup= TestSupplier::new;
 * 		System.out.println("--------");
 * 		//调用get()方法，此时会调用对象的构造方法，即获得到真正对象
 * 		sup.get();
 * 		//每次get都会调用构造方法，即获取的对象不同
 * 		sup.get();
 *    }
 *}
 * }
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public class AppThreadCache {

    private static final ThreadLocal<ThreadContext> CACHE = ThreadLocal.withInitial(ThreadContext::new);

    private static class ThreadContext {

        /**
         * 接入许可
         */
        String appId;
        /**
         * The App key.
         */
        String appKey;
        /**
         * The App secret.
         */
        String appSecret;
        /**
         * The App signature.
         */
        String appSignature;

        /**
         * The X forwarded for.
         */
        String xForwardedFor;
        /**
         * The Referer.
         */
        String referer;

        /**
         * The Service name.
         */
        String serviceName;
        /**
         * The Product name.
         */
        String productName;
        /**
         * The Module name.
         */
        String moduleName;
        /**
         * The Version.
         */
        String version;
        /**
         * 访问令牌
         */
        String ssId;
        /**
         * The Vf id.
         */
        String vfId;

        /**
         * 查看范围主键
         */
        String scopeId;

        /**
         * The Start time.
         */
        long startTime;
        /**
         * The Extra data.
         */
        Map<String, Object> extraData;
    }

    /**
     * Sets extra data.
     *
     */
    public static void setExtraData(Map<String, Object> map) {
        CACHE.get().extraData = map;
    }

    /**
     * Gets extra data.
     *
     * @return the extra data
     */
    public static Map<String, Object> getExtraData() {
        return CACHE.get().extraData;
    }

    /**
     * Sets start time.
     */
    public static void setStartTime() {
        CACHE.get().startTime = System.currentTimeMillis();
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public static long getStartTime() {
        return CACHE.get().startTime;
    }

    /**
     * Get ss id string.
     *
     * @return the string
     * @since 2019.04.24
     */
    public static String getSsId(){
        return CACHE.get().ssId;
    }

    /**
     * Sets ss id.
     *
     * @param ssId the ss id
     */
    public static void setSsId(String ssId) {
        CACHE.get().ssId = ssId;
    }

    public static String getScopeId(){
        return CACHE.get().scopeId;
    }

    public static void setScopeId(String scopeId) {
        CACHE.get().scopeId = scopeId;
    }


    /**
     * Get service name string.
     *
     * @return the string
     * @since 2019.04.24
     */
    public static String getServiceName(){
        return CACHE.get().serviceName;
    }

    /**
     * Sets service name.
     *
     * @param name the name
     */
    public static void setServiceName(String name) {
        CACHE.get().serviceName = name;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public static String getProductName() {
        return CACHE.get().productName;
    }

    /**
     * Sets product name.
     *
     * @param productName the product name
     */
    public static void setProductName(String productName) {
        CACHE.get().productName = productName;
    }

    /**
     * Gets module name.
     *
     * @return the module name
     */
    public static String getModuleName() {
        return CACHE.get().moduleName;
    }

    /**
     * Sets module name.
     *
     * @param moduleName the module name
     */
    public static void setModuleName(String moduleName) {
        CACHE.get().moduleName = moduleName;
    }

    /**
     * Get version string.
     *
     * @return the string
     * @since 2019.04.24
     */
    public static String getVersion(){
        return CACHE.get().version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public static void setVersion(String version) {
        CACHE.get().version = version;
    }

    /**
     * Get vf id string.
     *
     * @return the string
     * @since 2019.04.24
     */
    public static String getVfId(){
        return CACHE.get().vfId;
    }

    /**
     * Sets vf id.
     *
     * @param vfId the vf id
     */
    public static void setVfId(String vfId) {
        CACHE.get().vfId = vfId;
    }

    /**
     * Gets app id.
     *
     * @return the app id
     */
    public static String getAppId() {
        return CACHE.get().appId;
    }

    /**
     * Sets app id.
     *
     * @param appId the app id
     */
    public static void setAppId(String appId) {
        CACHE.get().appId = appId;
    }

    /**
     * Gets app key.
     *
     * @return the app key
     */
    public static String getAppKey() {
        return CACHE.get().appKey;
    }

    /**
     * Sets app key.
     *
     * @param appKey the app key
     */
    public static void setAppKey(String appKey) {
        CACHE.get().appKey = appKey;
    }

    /**
     * Gets app secret.
     *
     * @return the app secret
     */
    public static String getAppSecret() {
        return CACHE.get().appSecret;
    }

    /**
     * Sets app secret.
     *
     * @param appSecret the app secret
     */
    public static void setAppSecret(String appSecret) {
        CACHE.get().appSecret = appSecret;
    }

    /**
     * Gets app signature.
     *
     * @return the app signature
     */
    public static String getAppSignature() {
        return CACHE.get().appSignature;
    }

    /**
     * Sets app signature.
     *
     * @param appSignature the app signature
     */
    public static void setAppSignature(String appSignature) {
        CACHE.get().appSignature = appSignature;
    }

    /**
     * Gets x forwarded for.
     *
     * @return the x forwarded for
     */
    public static String getXForwardedFor() {
        return CACHE.get().xForwardedFor;
    }

    /**
     * Sets x forwarded for.
     *
     * @param xForwardedFor the x forwarded for
     */
    public static void setXForwardedFor(String xForwardedFor) {
        CACHE.get().xForwardedFor = xForwardedFor;
    }

    /**
     * Gets referer.
     *
     * @return the referer
     */
    public static String getReferer() {
        return CACHE.get().referer;
    }

    /**
     * Sets referer.
     *
     * @param referer the referer
     */
    public static void setReferer(String referer) {
        CACHE.get().referer = referer;
    }
}

