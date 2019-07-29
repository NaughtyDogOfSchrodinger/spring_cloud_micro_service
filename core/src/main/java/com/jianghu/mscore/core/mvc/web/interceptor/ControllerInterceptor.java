package com.jianghu.mscore.core.mvc.web.interceptor;

import com.jianghu.mscore.core.annotation.ExcludeXss;
import com.jianghu.mscore.util.ReflectUtil;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * controller的拦截器
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.26
 */
@Aspect
@Component
public class ControllerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

    /**
     * 声明切入点,所有controller的public方法
     *
     * @since 2019.04.26
     */
    @Pointcut("execution(public * com.jianghu.*.*.controller.*.*(..))")
    public void anyMethod() {
    }

    /**
     * 过滤参数.让参数中的空字符串("")变成null
     *
     * @param joinPoint the join point
     * @since 2019.04.26
     */
    @Before("anyMethod()")
    public void filterArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                filterEmptyString2Null(arg);
            }
        }
    }

    /**
     * 过滤空字符为null
     *
     * @param arg
     * @author zhaolei
     * @created 2012-6-1
     */
    private void filterEmptyString2Null(Object arg) {
        if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
            return;
        }
        if (arg != null && !(arg instanceof Enum)) {
            if (arg.getClass().getPackage() != null
                    && arg.getClass().getPackage().getName().startsWith("com.jianghu")) {
                Method[] methods = ReflectUtil.getPublicGetMethods(arg.getClass());
                for (Method m : methods) {
                    try {
                        Object value = m.invoke(arg);
                        if (value instanceof String && "".equals(value)) {
                            Method setMethod = ReflectUtil
                                    .getSetMethod4GetMethod(m, arg.getClass());
                            if (setMethod != null) {
                                setMethod.invoke(arg, new Object[]{null});
                            }
                        } else if (value != null) {
                            filterEmptyString2Null(value);
                        }
                    } catch (Exception e) {
                        if (this.realError(e.getCause())) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            } else if (arg instanceof String) {
                if ("".equals(arg.toString())) {
                    arg = null;
                }
            }
        }
    }

    /**
     * 过滤参数.让参数中的特殊字符转义
     *
     * @param joinPoint the join point
     * @author zhaolei
     * @created 2012 -4-24
     * @since 2019.04.26
     */
    @Before("anyMethod()")
    public void filterSpecialString(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        ExcludeXss excludeXss = signature.getMethod().getAnnotation(ExcludeXss.class);
        if (excludeXss == null && args != null) {
            for (Object arg : args) {
                executeFiltSpecial(arg);
            }
        }
    }

    /**
     * 过滤特殊字符，防止XSS漏洞
     *
     * @param arg
     */
    private void executeFiltSpecial(Object arg) {
        if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
            return;
        }
        if (arg != null && !(arg instanceof Enum)) {
            if (arg.getClass().getPackage() != null
                    && arg.getClass().getPackage().getName().startsWith("com.jianghu")) {
                Method[] methods = ReflectUtil.getPublicGetMethods(arg.getClass());
                for (Method m : methods) {
                    try {
                        Object value = m.invoke(arg);
                        if (value instanceof String) {
                            if (StringUtils.isWhitespace((String) value)) {
                                value = null;
                            }
                            Method setMethod = ReflectUtil.getSetMethod4GetMethod(m, arg.getClass());
                            if (setMethod != null) {
                                setMethod.invoke(arg, StringUtils.replaceEach((String) value, new String[]{"&", "\"", "<", ">", "\t"}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;", " "}));
                            }
                        }
                    } catch (Exception e) {
                        if (this.realError(e.getCause())) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }

    /**
     * 精确定义反射类error
     */
    private boolean realError(Throwable e) {
        if (e == null) {
            return true;
        }

        return !(e instanceof RuntimeException) && !(e instanceof Error);
    }

    /**
     * 给所有的返回为ModelAndView增加静态方法支持
     *
     * @param joinPoint the join point
     * @param returnObj the return obj
     * @since 2019.04.26
     */
    @AfterReturning(pointcut = "anyMethod()", returning = "returnObj")
    public void after(JoinPoint joinPoint, Object returnObj) {
        if (returnObj instanceof ModelAndView) {
            ((ModelAndView) returnObj).addObject("statics", new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build()
                    .getStaticModels());
        }

    }

    /**
     * 异常拦截
     *
     * @param joinPoint the join point
     * @param ex        the ex
     * @since 2019.04.26
     */
    @AfterThrowing(value = "anyMethod()", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Throwable ex) {

    }

}

