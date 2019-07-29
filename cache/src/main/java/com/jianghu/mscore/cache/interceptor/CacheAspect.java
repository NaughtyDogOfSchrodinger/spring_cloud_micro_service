package com.jianghu.mscore.cache.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jianghu.mscore.cache.annotation.MspCache;
import com.jianghu.mscore.cache.handler.RedisValueHandler;
import com.jianghu.mscore.util.ObjectUtil;
import com.jianghu.mscore.util.ReflectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * The type Cache aspect.
 * Description
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
@Aspect
@Component
public class CacheAspect {
    private static final Logger log = LoggerFactory.getLogger(CacheAspect.class);
    @Resource
    private RedisValueHandler redisValueHandler;

    /**
     * The type Cache aspect.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.06.11
     */
    public CacheAspect() {
    }


    /**
     * 拦截所有带有{@link MspCache}注解的方法
     *
     * @param j the j
     * @return the object
     * @throws Throwable the throwable
     * @since 2019.06.11
     */
    @Around("execution(* *.*(..)) && @annotation(com.jianghu.mscore.cache.annotation.MspCache)")
    public Object checkPrivilege(ProceedingJoinPoint j) throws Throwable {
        Object[] args = j.getArgs();
        Method m = ReflectUtil.getMethod(j);
        MspCache cache = AnnotationUtils.findAnnotation(m, MspCache.class);
        if (cache == null) return j.proceed(args);
        int keepSecond = cache.keepSecond();
        boolean reCache = cache.reCache();
        String cacheKey = cache.cacheKey();
        List<Object> argsList = Lists.newArrayList(args);
        if (keepSecond < 0) {
            return j.proceed(args);
        } else {
            if (!StringUtils.hasLength(cacheKey)) {
                cacheKey = m.getDeclaringClass().toString() + "#" + m.getName() + this.getCacheKey(argsList.toArray());
                log.debug("cache - original KEY:{}", cacheKey);
            }

            Object rtn = redisValueHandler.get(cacheKey);
            if (!ObjectUtil.isNullObj(rtn) && !reCache) {
                return rtn;
            } else {
                rtn = j.proceed(args);
                if (rtn != null && (!(rtn instanceof Collection) || ((Collection)rtn).size() != 0) && (!(rtn instanceof Map) || ((Map)rtn).keySet().size() != 0) && (!rtn.getClass().isArray() || (((Object[])rtn)).length != 0)) {
                    log.debug("cache into medis");
                    redisValueHandler.set(cacheKey, rtn, keepSecond, TimeUnit.SECONDS);
                }

                return rtn;
            }
        }
    }

    private String getCacheKey(Object... o) {
        StringBuilder sb = new StringBuilder();
        Stream.of(o).forEach((object) -> {
            sb.append(this.object2KeyStr(object)).append("&*&");
        });
        return sb.toString();
    }

    private String object2KeyStr(Object object) {
        if (object == null) {
            return "NULL";
        } else {
            return !(object instanceof Collection) && !(object instanceof Map) && !object.getClass().isEnum() && !object.getClass().isArray() ? object.toString() : ((JSON)JSON.toJSON(object)).toJSONString();
        }
    }
}

