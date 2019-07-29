package com.jianghu.mscore.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Maps;
import com.jianghu.mscore.cache.handler.RedisValueHandler;
import com.jianghu.mscore.util.RandomUtil;
import com.jianghu.mscore.web.config.properties.ApplicationProperties;
import com.jianghu.mscore.web.constant.AuthUrl;
import com.jianghu.mscore.web.constant.DateFormat;
import com.jianghu.mscore.web.constant.PlatformEnum;
import com.jianghu.mscore.web.constant.PropertiesKey;
import com.jianghu.mscore.web.exception.AuthenticationException;
import com.jianghu.mscore.web.util.HttpUtil;
import com.jianghu.mscore.web.util.RmcResultWrapper;
import com.jianghu.mscore.web.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 授权服务层
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.20
 */
@Service
public class AuthenticationService extends AbstractService {

    private static final long ONE_MOUTH = 30 * 24 * 60 * 60L;


    private Object getOrUpdateUserInfo(String secondKey) {
        Object authentication;
        if (ObjectUtils.isEmpty(authentication = redisValueHandler.get(secondKey))) {
            throw new AuthenticationException("该用户不存在");
        } else {
            return authentication;
        }
    }

    @Resource
    private RedisValueHandler redisValueHandler;


    /**
     * 获取缓存授权
     *
     * @return the authentication cached
     */
    public HashMap getAuthenticationCached() {
        try {
            String ssid = getSessionId();
            if (StringUtils.isEmpty(ssid)) {
                throw new AuthenticationException("请重新登录获取授权");
            }
            String firstKey = getSsIdKey(ssid);
            Object secondKey =redisValueHandler.get(firstKey);
            if (ObjectUtils.isEmpty(secondKey)) {
                throw new AuthenticationException("会话已过期,请重新登录");
            }
            return new ObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .setDateFormat(DateFormat.DEFAULT_LONG_FORMAT)
                    .convertValue(getOrUpdateUserInfo((String) secondKey), HashMap.class);
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    private boolean isPhone() {
        return getPlatform() == PlatformEnum.IOS || getPlatform() == PlatformEnum.Android;
    }

    /**
     * 添加授权
     *
     * @param authentication the authentication
     * @return the map
     * @since 2018.12.20
     */
    public String postAuthentication(Object authentication) {
        //正常调用创建认证信息方法
        String ssid = RandomUtil.generateUuid();
        String firstKey = getSsIdKey(ssid);
        String secondKey = PropertiesKey.USER_INFO_SECOND_PREFIX + ((Map) authentication).get("staffId");
        redisValueHandler.set(firstKey, secondKey, ONE_MOUTH, TimeUnit.SECONDS);
        redisValueHandler.set(secondKey, authentication);
        return ssid;
    }


    /**
     * 删除授权
     *
     * @param ssids the ssids
     * @since 2018.12.20
     */
    public void deleteAuthentication(List<String> ssids) {
        redisValueHandler.delete(ssids);
    }



}
