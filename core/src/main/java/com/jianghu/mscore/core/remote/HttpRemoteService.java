package com.jianghu.mscore.core.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jianghu.mscore.api.CommonErrorCode;
import com.jianghu.mscore.api.Error;
import com.jianghu.mscore.core.exception.AppBusinessException;
import com.jianghu.mscore.core.exception.RemoteCallException;
import com.jianghu.mscore.util.ObjectUtil;
import com.jianghu.mscore.util.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * The type Http remote service.
 * Description
 *
 * @author hujiang.
 * @version 1.0
 * @Description 封装api基类
 * @since 2019.04.26
 */
@Deprecated
public class HttpRemoteService {

    /**
     * The Logger.
     */
    protected Logger logger = LoggerFactory.getLogger(HttpRemoteService.class);

    @Autowired
    private RestTemplate restTemplate;

    private static String DATA = "data";
    private static String STATUS = "status";
    private static String ERROR = "error";

    /**
     * Invoke t.
     *
     * @param <T>           the type parameter
     * @param response      the response
     * @param typeReference the type reference
     * @return the t
     * @since 2019.04.26
     */
    public <T> T invoke(Map<String, Object> response, TypeReference<T> typeReference) {
        if (MapUtils.isEmpty(response)) {
            throw new AppBusinessException("请求失败!");
        }
        Integer status = MapUtils.getInteger(response, STATUS);
        // 请求失败的处理
        if (ObjectUtil.isNullObj(status)
                || status.equals(NumberUtils.INTEGER_ZERO)) {
            Map map = MapUtils.getMap(response, ERROR);
            Error error =  new Error(MapUtils.getString(map,"code"), MapUtils.getString(map,"requestUri"), MapUtils.getString(map,"message"));
            throw new RemoteCallException(error, NumberUtils.toInt(MapUtils.getString(map,"code")));
        }

        String data = MapUtils.getString(response, DATA);
        if (StringUtil.isEmpty(data)) {
            throw new RemoteCallException(getDefaultError(), NumberUtils.toInt(getDefaultError().getCode()));
        }
        try {
            return (T) JSON.parseObject(data, typeReference);
        } catch (Exception e) {
            throw new RemoteCallException(getDefaultError(), NumberUtils.toInt(getDefaultError().getCode()));
        }
    }

    /**
     * get template
     *
     * @param <T>           the type parameter
     * @param url           the url
     * @param uriVariables  the uri variables
     * @param typeReference the type reference
     * @return t
     * @since 2019.04.26
     */
    public <T> T invokeGet(String url, Map<String, Object> uriVariables, TypeReference<T> typeReference) {
        // check
        this.invokeGetCheck(url);
        Map<String, Object> result;
        try {
            result = this.restTemplate.getForObject(url, Map.class, uriVariables);
        } catch (Exception e) {
            logger.error("HttpRemoteService invokeGet error url:{},uriVariables:{},typeReference:{}", url, ObjectUtil.toString(uriVariables), ObjectUtil.toString(typeReference));
            throw new RemoteCallException(getDefaultError(), NumberUtils.toInt(getDefaultError().getCode()));
        }
        return this.invoke(result, typeReference);
    }

    private void invokeGetCheck(String url) {
        if (StringUtil.isEmpty(url)) {
            throw new RemoteCallException(new Error(CommonErrorCode.BAD_REQUEST.getCode(), "", "请求的URL为空!")
                    , NumberUtils.toInt(CommonErrorCode.BAD_REQUEST.getCode()));
        }
    }


    private Error getDefaultError() {
        return new Error(CommonErrorCode.INTERNAL_ERROR.getCode(), "", "发生未知错误!");
    }
}
