package com.jianghu.mscore.context.constant;

/**
 * 系统级令牌常量注入
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public interface TokenManager {

    /**
     * 接入许可
     */
    String APP_ID = "app-id";
    /**
     * The constant APP_KEY.
     */
    String APP_KEY = "app-key";
    /**
     * The constant APP_SECRET.
     */
    String APP_SECRET = "app-secret";
    /**
     * The constant APP_SIGNATURE.
     */
    String APP_SIGNATURE = "app-signature";
    /**
     * The constant X_FORWARDED_FOR.
     */
    String X_FORWARDED_FOR = "x-forwarded-for";
    /**
     * The constant REFERER.
     */
    String REFERER = "referer";
}


