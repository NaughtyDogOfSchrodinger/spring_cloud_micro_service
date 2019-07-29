package com.jianghu.mscore.web.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 鉴权配置类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
@Configuration
@ConfigurationProperties(prefix = "server.properties")
public class ApplicationProperties {
    //该应用域名
    private String domain;

    //api接口域名
    private String apiDomain;

    private String sessionKey;

    private String loginUrl;

    private String authenticationUrl;

    private List<String> containUrl;//排除url中包含的字段

    private List<String> excludePrefix;//排除的urls前缀

    private List<String> excludeUrls;//排除的urls

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getApiDomain() {
        return apiDomain;
    }

    public void setApiDomain(String apiDomain) {
        this.apiDomain = apiDomain;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getAuthenticationUrl() {
        return authenticationUrl;
    }

    public void setAuthenticationUrl(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }

    public List<String> getContainUrl() {
        return containUrl;
    }

    public void setContainUrl(List<String> containUrl) {
        this.containUrl = containUrl;
    }

    public List<String> getExcludePrefix() {
        return excludePrefix;
    }

    public void setExcludePrefix(List<String> excludePrefix) {
        this.excludePrefix = excludePrefix;
    }

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
