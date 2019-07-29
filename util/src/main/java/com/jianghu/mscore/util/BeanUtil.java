package com.jianghu.mscore.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * map javabean转换工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.01.21
 */
public class BeanUtil {

    /**
     *  map 转 bean.
     *
     * @param map the map
     * @param obj the obj
     * @since 2019.01.21
     */
    public static void transMap2Bean(Map<String, ?> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    try {
                        Object value = map.get(key);
                        if (value == null
                                || value.toString().equalsIgnoreCase("null")) {
                            continue;
                        }
                        // 得到property对应的setter方法
                        Method setter = property.getWriteMethod();
                        Class<?> propertyClass = setter.getParameterTypes()[0];


                        setter.invoke(obj, propertyClass.cast(value));
                    } catch (Exception ignored) {
                    }
                }

            }

        } catch (Exception ignored) {

        }

    }

    /**
     * map 转 bean.
     *
     * @param <T>    the type parameter
     * @param map    the map
     * @param class1 the class 1
     * @return the t
     * @since 2019.01.21
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> class1) {
        T bean = null;
        try {
            bean = class1.newInstance();
            BeanUtils.populate(bean, map);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * bean转map
     *
     * @param obj the obj
     * @return the map
     * @since 2019.01.21
     */
    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }


    /**
     * 对象转字符串
     *
     * @param object the object
     * @return the string
     * @since 2019.01.21
     */
    public static String objectToString(Object object) {
        return ToStringBuilder.reflectionToString(object);
    }
}
