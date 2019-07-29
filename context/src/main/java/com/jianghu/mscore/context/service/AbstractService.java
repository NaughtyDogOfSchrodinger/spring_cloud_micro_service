package com.jianghu.mscore.context.service;

import com.github.pagehelper.PageInfo;
import com.jianghu.mscore.api.PageVo;
import com.jianghu.mscore.context.config.AppThreadCache;
import com.jianghu.mscore.context.config.ApplicationConfigurer;
import com.jianghu.mscore.context.config.ErrorConfigurer;
import com.jianghu.mscore.context.data.base.InterfaceMapper;
import com.jianghu.mscore.context.remote.InterfaceFeignClient;
import com.jianghu.mscore.context.remote.RmcInvoker;
import com.jianghu.mscore.exception.MspException;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Abstract service.
 * Description
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public abstract class AbstractService {
    /**
     * The Logger.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired(required = false)
    private RmcInvoker rmcInvoker;

    @Autowired(required = false)
    private Map<String, InterfaceMapper<?>> mybatisMapperFactory;

    @Autowired(required = false)
    private Map<String, InterfaceFeignClient> feignClientFactory;

    /**
     * Gets logger.
     *
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * Gets service name.
     *
     * @return the service name
     */
    protected String getServiceName() {
        return AppThreadCache.getServiceName();
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    protected String getProductName() {
        return AppThreadCache.getProductName();
    }

    /**
     * Get version string.
     *
     * @return the string
     * @since 2019.04.24
     */
    protected String getVersion(){
        return AppThreadCache.getVersion();
    }

    /**
     * Get module name string.
     *
     * @return the string
     * @since 2019.04.24
     */
    protected String getModuleName(){
        return AppThreadCache.getModuleName();
    }

    /**
     * Gets ss id.
     *
     * @return the ss id
     */
    protected String getSsId() {
        return AppThreadCache.getSsId();
    }

    /**
     * Gets vf id.
     *
     * @return the vf id
     */
    protected String getVfId() {
        return AppThreadCache.getVfId();
    }

    /**
     * Gets x forwarded for.
     *
     * @return the x forwarded for
     */
    protected String getXForwardedFor() {
        return AppThreadCache.getXForwardedFor();
    }

    /**
     * Gets referer.
     *
     * @return the referer
     */
    protected String getReferer() {
        return AppThreadCache.getReferer();
    }

    /**
     * Gets rmc invoker.
     *
     * @return the rmc invoker
     */
    protected RmcInvoker getRmcInvoker() {
        return rmcInvoker;
    }

    /**
     * Gets mybatis mapper.
     *
     * @param <T>             the type parameter
     * @param mapperInterface the mapper interface
     * @return the mybatis mapper
     */
    protected <T> T getMybatisMapper(Class<T> mapperInterface) {
        String mapperClassName = mapperInterface.getSimpleName();
        if(Character.isLowerCase(mapperClassName.charAt(0))){
            return null;
        }
        if (Character.isLowerCase(mapperClassName.charAt(1))) {
            char[] charArray = mapperClassName.toCharArray();
            charArray[0] += 32;
            mapperClassName=String.valueOf(charArray);
        }
        return (T) mybatisMapperFactory.get(mapperClassName);
    }

    /**
     * Gets feign client.
     *
     * @param <T>                  the type parameter
     * @param feignClientInterface the feign client interface
     * @return the feign client
     */
    protected <T> T getFeignClient(Class<T> feignClientInterface) {
        return (T) feignClientFactory.get(feignClientInterface.getName());
    }

    /**
     * Gets error code.
     *
     * @param nodeCode the node code
     * @return the error code
     */
    protected String getErrorCode(String nodeCode) {
        return errorConfigurer.getErrorCode(nodeCode);
    }

    /**
     * Gets error msg.
     *
     * @param nodeCode the node code
     * @return the error msg
     */
    protected String getErrorMsg(String nodeCode) {
        return errorConfigurer.getErrorMsg(nodeCode);
    }

    /**
     * Gets error node.
     *
     * @param nodeCode the node code
     * @return the error node
     */
    protected Map<String, Object> getErrorNode(String nodeCode) {
        return errorConfigurer.getErrorNode(nodeCode);
    }


    /**
     * Gets application configurer.
     *
     * @return the application configurer
     */
    protected ApplicationConfigurer getApplicationConfigurer() {
        return applicationConfigurer;
    }

    /**
     * The Error configurer.
     */
    @Resource
    protected ErrorConfigurer errorConfigurer;

    /**
     * The Application configurer.
     */
    @Resource
    protected ApplicationConfigurer applicationConfigurer;

    protected <T> void addPageInfo(List<T> list, PageVo pageVo) {
        PageInfo<T> info = new PageInfo<>(list);
        pageVo.setTotalCount(NumberUtils.toInt(info.getTotal() + ""));
        pageVo.setTotalPageNum(info.getPages());
    }

    protected Object getResult(Map<String, Object> map) {
        if (Objects.equals(NumberUtils.INTEGER_ZERO, map.get("status"))) {
            throw new MspException(String.valueOf(map.get("msg")));
        }
        return map.get("data");
    }


}

