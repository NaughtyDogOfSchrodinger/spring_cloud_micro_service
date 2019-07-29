package com.jianghu.mscore.web.controller;

import com.google.common.collect.Maps;
import com.jianghu.mscore.api.CommonErrorCode;
import com.jianghu.mscore.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.jianghu.mscore.api.Error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 统一异常处理
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.26
 */
@RequestMapping("${server.error.path:${error.path:/error}}")
@Controller
public class MyErrorController extends AbstractErrorController {
    private ErrorProperties errorProperties;


    /**
     * The type App error controller.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.04.26
     */
    @Autowired
    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes);
        Assert.notNull(errorProperties, "ErrorProperties must not be null");
        this.errorProperties = errorProperties;
    }

    /**
     * Gets error path.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    /**
     * Error html model and view.
     *
     * @param request  the request
     * @param response the response
     * @return the model and view
     * @since 2019.04.26
     */
    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        CommonErrorCode errorCode = CommonErrorCode.fromHttpStatus(status.value());
        Error error = new Error(errorCode.getCode(), request.getRequestURI(), status.getReasonPhrase());
        ModelAndView modelAndView = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView(JsonUtils.OBJECT_MAPPER);
        view.setAttributesMap(JsonUtils.object2Map(error));
        modelAndView.setView(view);
        return modelAndView;
    }


    /**
     * Error response entity.
     *
     * @param request the request
     * @return the response entity
     * @since 2019.04.26
     */
    @RequestMapping
    @ResponseBody
    public ResponseEntity<String> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        CommonErrorCode errorCode = CommonErrorCode.fromHttpStatus(status.value());
        Error error = new Error(errorCode.getCode(), request.getRequestURI(), status.getReasonPhrase());
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("error", error);
        ret.put("msg", error.getMessage());
        return new ResponseEntity<>(JsonUtils.object2Json(ret), status);
    }
}


