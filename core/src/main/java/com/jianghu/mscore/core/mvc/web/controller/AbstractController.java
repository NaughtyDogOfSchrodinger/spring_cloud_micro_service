package com.jianghu.mscore.core.mvc.web.controller;


import com.jianghu.mscore.api.AjaxResult;
import com.jianghu.mscore.api.Page;
import com.jianghu.mscore.context.service.AbstractService;
import com.jianghu.mscore.core.annotation.MspController;
import com.jianghu.mscore.core.annotation.Permission;
import com.jianghu.mscore.core.vo.SectionVo;
import com.jianghu.mscore.exception.BaseException;
import com.jianghu.mscore.util.ClassUtil;
import com.jianghu.mscore.util.StringUtil;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;


/**
 * 自定义控制器
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.05.07
 */
public abstract class AbstractController {
    /**
     * The Logger.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired(required = false)
    private Map<String, AbstractService> serviceObjectFactory;

    @Resource
    private SectionVo sectionVo;

    /**
     * Gets service object.
     *
     * @param <T> the type parameter
     * @param cls the cls
     * @return the service object
     */
    public <T> T getServiceObject(Class<T> cls) {
        String className = cls.getSimpleName();
        if(Character.isLowerCase(className.charAt(0))){
            return null;
        }
        if (Character.isLowerCase(className.charAt(1))) {
            char[] charArray = className.toCharArray();
            charArray[0] += 32;
            className=String.valueOf(charArray);
        }
        return (T) serviceObjectFactory.get(className);
    }

    /**
     * Exception handler map.
     *
     * @param e the e
     * @return the map
     * @since 2019.05.07
     */
    protected Map<String, Object> exceptionHandler(Exception e) {
        if(e instanceof BaseException){
            logger.info(e.getMessage());
            return failure( e.getMessage());
        }else if (e instanceof HystrixRuntimeException || e instanceof HystrixTimeoutException){
            logger.error("Rpc异常", e);
            return failure("Rpc异常");
        } else {
            logger.error("AbstractController unknown error", e);
            return failure( "程序异常");
        }
    }

    /**
     * Exception handler ui model and view.
     *
     * @param e the e
     * @return the model and view
     * @since 2019.05.07
     */
    protected ModelAndView exceptionHandlerUi(Exception e) {
        if(e instanceof BaseException){
            logger.info(e.getMessage());
            return failureUi("failure",e.getMessage());
        }else{
            logger.error("AbstractController unknown error", e);
            return failureUi("failure",e.getStackTrace());
        }
    }

    /**
     * Success map.
     *
     * @return the map
     * @since 2019.05.07
     */
    protected Map<String, Object> success() {
        return AjaxResult.createAjaxSuccessMap();
    }

    /**
     * Success map.
     *
     * @param msg the msg
     * @return the map
     * @since 2019.05.07
     */
    protected Map<String, Object> success(String msg) {
        return AjaxResult.createAjaxSuccessMap(msg);
    }

    /**
     * Success map.
     *
     * @param msg  the msg
     * @param data the data
     * @return the map
     * @since 2019.05.07
     */
    protected Map<String, Object> success(String msg,Object data) {
        return AjaxResult.createAjaxSuccessMap(data,msg);
    }

    /**
     * Success map.
     *
     * @param msg  the msg
     * @param data the data
     * @param page the page
     * @return the map
     * @since 2019.05.07
     */
    protected Map<String, Object> success(String msg, Object data, Page page) {
        return AjaxResult.createAjaxSuccessMap(msg, data, page);
    }

    /**
     * Failure map.
     *
     * @return the map
     * @since 2019.05.07
     */
    protected Map<String, Object> failure() {
        return AjaxResult.createAjaxFailMap();
    }

    /**
     * Failure map.
     *
     * @param msg the msg
     * @return the map
     * @since 2019.05.07
     */
    protected Map<String, Object> failure(String msg) {
        return AjaxResult.createAjaxFailMap(msg);
    }

    /**
     * Failure map.
     *
     * @param msg  the msg
     * @param data the data
     * @return the map
     * @since 2019.05.07
     */
    protected Map<String, Object> failure(String msg,Object data) {
        return AjaxResult.createAjaxFailMap(data,msg);
    }

    /**
     * Failure ui model and view.
     *
     * @return the model and view
     * @since 2019.05.07
     */
    protected ModelAndView failureUi() {
        return failureUi("failure");
    }

    /**
     * Failure ui model and view.
     *
     * @param vo the vo
     * @return the model and view
     * @since 2019.05.07
     */
    protected ModelAndView failureUi(String vo) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(vo);
        return modelAndView;
    }

    /**
     * Failure ui model and view.
     *
     * @param vo   the vo
     * @param data the data
     * @return the model and view
     * @since 2019.05.07
     */
    protected ModelAndView failureUi(String vo, Object data) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data",data);
        modelAndView.setViewName(vo);
        return modelAndView;
    }

    /**
     * Success ui model and view.
     *
     * @return the model and view
     * @since 2019.05.07
     */
    protected ModelAndView successUi() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * Success ui model and view.
     *
     * @param vo the vo
     * @return the model and view
     * @since 2019.05.07
     */
    protected ModelAndView successUi(String vo) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(vo);
        return modelAndView;
    }

    /**
     * Success ui model and view.
     *
     * @param vo   the vo
     * @param data the data
     * @return the model and view
     * @since 2019.05.07
     */
    protected ModelAndView successUi(String vo, Object data) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data",data);
        modelAndView.setViewName(vo);
        return modelAndView;
    }


    /**
     * Generate authority authority vo.
     *
     * @since 2019.05.07
     */
    public SectionVo generateAuthority(String packageName) {
        Set<Class<?>> set = ClassUtil.getClassSet(packageName);
        Map<SectionVo.Section, SectionVo.Section> sectionMap = new HashMap<>();
        Set<String> urlPrefixs = new HashSet<>();
        for (Class<?> clazz : set) {
            MspController mspController = clazz.getAnnotation(MspController.class);
            if (mspController == null) continue;
            String urlPrefix = mspController.value()[0];
            if (!urlPrefix.startsWith("/")) urlPrefix = "/" + urlPrefix;
            urlPrefixs.add(urlPrefix);
            Api api = clazz.getAnnotation(Api.class);
            Permission permission = clazz.getAnnotation(Permission.class);
            if (permission == null || api == null || StringUtil.isEmpty(api.value())) continue;
            for (Method method : clazz.getDeclaredMethods()) {
                ApiOperation operation = method.getAnnotation(ApiOperation.class);
                Permission methodPerm = method.getAnnotation(Permission.class);
                RequestMapping mappingAnnotation = method.getAnnotation(RequestMapping.class);
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                if ((mappingAnnotation == null && postMapping == null && getMapping == null) || operation == null || StringUtil.isEmpty(operation.value())) continue;
                String url = getMapping != null ? getMapping.value()[0] : (postMapping != null ? postMapping.value()[0] : mappingAnnotation.value()[0]);
                if (!url.startsWith("/")) url = "/" + url;
                SectionVo.Section section = new SectionVo.Section();
                section.setSectionName(api.value());
                section.setSectionDetail(api.tags()[0]);
                section.setController(clazz.getName());
                section.setShowOrder(permission.showOrder());
                if (methodPerm != null) {
                    if (sectionMap.containsKey(section)) {
                        sectionMap.get(section).setSectionUrl(urlPrefix + url);
                    } else {
                        section.setSectionUrl(urlPrefix + url);
                        sectionMap.put(section, section);
                    }

                } else {
                    SectionVo.Authority authority = new SectionVo.Authority();
                    authority.setPermissionName(operation.value());
                    authority.setActionKey(urlPrefix + url);
                    authority.setPermissionDetail(operation.notes());
                    if (!sectionMap.containsKey(section)) {
                        sectionMap.put(section, section);
                    }
                    sectionMap.get(section).getAuthorityList().add(authority);

                }
            }
        }
        sectionVo.setUrlPrefix(urlPrefixs).setSectionList(new ArrayList<>(sectionMap.keySet()));
        return sectionVo;
    }

}

