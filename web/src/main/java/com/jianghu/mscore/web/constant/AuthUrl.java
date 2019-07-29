package com.jianghu.mscore.web.constant;

import com.jianghu.mscore.web.config.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class AuthUrl extends ApplicationProperties {

    public String check(){
        return getApiDomain() + "/setting/authority/check";
    }
}
