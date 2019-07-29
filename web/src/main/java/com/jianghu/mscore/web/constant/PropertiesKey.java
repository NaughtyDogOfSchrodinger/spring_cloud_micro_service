package com.jianghu.mscore.web.constant;

/**
 * 配置文件，线程变量缓存key
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.29
 */
public interface PropertiesKey {

    /**
     * 默认配置
     */
    String DEFAULT_PROPERTIES_KEY = "default_properties_key";
    /**
     * 短信配置
     */
    String MESSAGE_PROPERTIES_KEY = "message_properties_key";
    /**
     * 权限key
     */
    String AUTHENTICATION_KEY = "authentication_key";

    /**
     * 用户信息二级key前缀
     */
    String USER_INFO_SECOND_PREFIX = "user_info_second_";
}
