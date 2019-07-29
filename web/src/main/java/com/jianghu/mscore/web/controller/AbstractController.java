package com.jianghu.mscore.web.controller;

import com.jianghu.mscore.exception.BaseException;
import com.jianghu.mscore.web.config.AppThreadCache;
import com.jianghu.mscore.web.config.properties.ApplicationProperties;
import com.jianghu.mscore.web.constant.PlatformEnum;
import com.jianghu.mscore.web.util.AjaxResult;
import com.jianghu.mscore.web.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 自定义控制器基类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
public abstract class AbstractController {

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Resource
    private ApplicationProperties applicationProperties;


    /**
     * 成功返回值
     *
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> success() {
        return AjaxResult.createAjaxSuccessMap();
    }

    /**
     * 成功返回值
     *
     * @param msg the msg
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> success(String msg) {
        return AjaxResult.createAjaxSuccessMap(msg);
    }

    /**
     * 成功返回值
     *
     * @param msg  the msg
     * @param data the data
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> success(String msg, Object data) {
        return AjaxResult.createAjaxSuccessMap(data, msg);
    }

    /**
     * 成功返回值
     *
     * @param data the data
     * @param page the page
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> success(Object data, Page page) {
        return AjaxResult.createAjaxSuccessMap(data, page);
    }

    /**
     * 成功返回值
     *
     * @param msg  the msg
     * @param data the data
     * @param page the page
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> success(String msg, Object data, Page page) {
        return AjaxResult.createAjaxSuccessMap(msg, data, page);
    }

    /**
     * 失败返回值
     *
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> failure() {
        return AjaxResult.createAjaxFailMap();
    }

    /**
     * 失败返回值
     *
     * @param msg the msg
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> failure(String msg) {
        return AjaxResult.createAjaxFailMap(msg);
    }

    /**
     * 失败返回值
     *
     * @param msg  the msg
     * @param data the data
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> failure(String msg, Object data) {
        return AjaxResult.createAjaxFailMap(data, msg);
    }

    /**
     * 失败返回值
     *
     * @param data the data
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> failure(Object data) {
        return AjaxResult.createAjaxFailMap(data);
    }


    /**
     * 获取缓存认证信息
     *
     * @return the cached authentication
     */
    public Map getCachedAuthentication() {
        return (Map) AppThreadCache.getExtraData("authentication_key");
    }

    /**
     * 获取请求头
     *
     * @return the platform
     */
    public PlatformEnum getPlatform() {
        return AppThreadCache.getPlatform();
    }

    protected String getXForwardedFor() {
        return AppThreadCache.getXForwardedFor();
    }



    /**
     * api异常处理类
     *
     * @param e the e
     * @return the map
     * @since 2018.12.19
     */
    protected Map<String, Object> exceptionHandler(Exception e) {
        if (e instanceof BaseException) {
            this.logger.info(e.getMessage());
            return this.failure(e.getMessage());
        } else {
            this.logger.error("AbstractController unknown error", e);
            return this.failure("程序异常");
        }
    }

    /**
     * ui异常处理类
     *
     * @param e the e
     * @return the model and view
     * @since 2018.12.19
     */
    protected ModelAndView exceptionHandlerUi(Exception e) {
        if (e instanceof BaseException) {
            this.logger.info(e.getMessage());
            return this.failureUi("failure", e.getMessage());
        } else {
            this.logger.error("AbstractController unknown error", e);
            return this.failureUi("failure", e.getStackTrace());
        }
    }

    /**
     * 失败ui
     *
     * @param vo the vo
     * @return the model and view
     * @since 2018.12.19
     */
    protected ModelAndView failureUi(String vo) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(vo);
        return modelAndView;
    }

    /**
     * 失败ui
     *
     * @param vo   the vo
     * @param data the data
     * @return the model and view
     * @since 2018.12.19
     */
    protected ModelAndView failureUi(String vo, Object data) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data", data);
        modelAndView.setViewName(vo);
        return modelAndView;
    }

    /**
     * 成功ui
     *
     * @return the model and view
     * @since 2018.12.19
     */
    protected ModelAndView successUi() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * 成功ui
     *
     * @param vo the vo
     * @return the model and view
     * @since 2018.12.19
     */
    protected ModelAndView successUi(String vo) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(vo);
        return modelAndView;
    }

    /**
     * 成功ui
     *
     * @param vo   the vo
     * @param data the data
     * @return the model and view
     * @since 2018.12.19
     */
    protected ModelAndView successUi(String vo, Object data) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data", data);
        modelAndView.setViewName(vo);
        return modelAndView;
    }


    public void threadLocalRemove() {
        AppThreadCache.remove();
    }


    private String getSessionIdKey() {
        return applicationProperties.getSessionKey();
    }


    protected boolean hasSession() {
        return AppThreadCache.getSessionId() != null;
    }

    protected boolean isLogin() {
        return hasSession() && getCachedAuthentication() != null;
    }

}
