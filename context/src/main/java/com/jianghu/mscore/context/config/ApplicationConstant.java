package com.jianghu.mscore.context.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * 系统级常量注入
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public class ApplicationConstant {

    /**
     * The Service name.
     */
    @Value("${spring.application.serviceName:null}")
    public String serviceName;

    /**
     * The Product name.
     */
    @Value("${spring.application.productName:null}")
    public String productName;

    /**
     * The Module name.
     */
    @Value("${spring.application.moduleName:null}")
    public String moduleName;

    /**
     * The Version.
     */
    @Value("${spring.application.version:null}")
    public String version;

    /**
     * The Ssid.
     */
    @Value("${spring.application.token.ssid:cs-ssid}")
    public String ssId;

    /**
     * The Vf id.
     */
    @Value("${spring.application.token.vfid:verfication-key}")
    public String vfId;

    @Value("${spring.application.token.scopeId:scopeId}")
    public String scopeId;

}

