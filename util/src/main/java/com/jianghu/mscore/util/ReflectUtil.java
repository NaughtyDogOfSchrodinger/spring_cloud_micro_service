package com.jianghu.mscore.util;

import com.google.common.collect.Lists;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/**
 * 反射工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.21
 */
public class ReflectUtil {
    private static final Logger log = LoggerFactory.getLogger(ReflectUtil.class);
    /**
     * The constant REGPATTERN.
     */
    public static final Pattern REGPATTERN = Pattern.compile("(?<=\\()\\S+(?=\\))");


    /**
     * 获取公共get方法
     *
     * @param clazz the clazz
     * @return the method [ ]
     * @since 2019.06.21
     */
    public static Method[] getPublicGetMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> methodList = Lists.newArrayList();

        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (methodName != null && !StringUtils.equals("get", methodName) && methodName.startsWith("get")) {
                methodList.add(method);
            }
        }

        return methodList.toArray(new Method[0]);
    }

    /**
     * 获取公共set方法
     *
     * @param clazz the clazz
     * @return the method [ ]
     * @since 2019.06.21
     */
    public static Method[] getPublicSetMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> methodList = Lists.newArrayList();

        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (methodName != null && !StringUtils.equals("set", methodName) && methodName.startsWith("set")) {
                methodList.add(method);
            }
        }

        return methodList.toArray(new Method[0]);
    }

    /**
     * 根据get方法返回set方法
     *
     * @param getMethod the get method
     * @param clazz     the clazz
     * @return the set method 4 get method
     */
    public static Method getSetMethod4GetMethod(Method getMethod, Class<?> clazz) {
        String setMethodName = getMethod.getName().replaceFirst("get", "set");
        return ReflectionUtils.findMethod(clazz, setMethodName, getMethod.getReturnType());
    }

    /**
     * 根据set方法返回get方法
     *
     * @param setMethod the set method
     * @param clazz     the clazz
     * @return the get method 4 set method
     */
    public static Method getGetMethod4SetMethod(Method setMethod, Class<?> clazz) {
        String getMethodName = setMethod.getName().replaceFirst("set", "get");
        return ReflectionUtils.findMethod(clazz, getMethodName);
    }

    /**
     * 获取指定域get方法名
     *
     * @param fieldName the field name
     * @return the get method name 4 field
     */
    public static String getGetMethodName4Field(String fieldName) {
        if (fieldName.length() == 0) {
            return "get";
        } else {
            return fieldName.length() == 1 ? "get" + fieldName.toUpperCase() : "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
    }

    /**
     * 获取指定域set方法名
     *
     * @param fieldName the field name
     * @return the set method name 4 field
     */
    public static String getSetMethodName4Field(String fieldName) {
        if (fieldName.length() == 0) {
            return "set";
        } else {
            return fieldName.length() == 1 ? "set" + fieldName.toUpperCase() : "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
    }

    /**
     * 获取指定域get方法
     *
     * @param clazz     the clazz
     * @param fieldName the field name
     * @return the get method 4 field
     * @throws SecurityException     the security exception
     * @throws NoSuchMethodException the no such method exception
     */
    public static Method getGetMethod4Field(Class<?> clazz, String fieldName) throws SecurityException, NoSuchMethodException {
        return clazz.getDeclaredMethod(getGetMethodName4Field(fieldName));
    }

    /**
     * 获取对象指定域get方法值
     *
     * @param <T>      the type parameter
     * @param obj      the obj
     * @param property the property
     * @return the value by property
     * @throws SecurityException         the security exception
     * @throws NoSuchMethodException     the no such method exception
     * @throws IllegalArgumentException  the illegal argument exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     */
    public static <T> Object getValueByProperty(T obj, String property) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return getGetMethod4Field(obj.getClass(), property).invoke(obj);
    }

    /**
     * 根据切点获取方法
     *
     * @param j the j
     * @return the method
     * @throws ClassNotFoundException the class not found exception
     * @throws SecurityException      the security exception
     * @throws NoSuchMethodException  the no such method exception
     */
    public static Method getMethod(ProceedingJoinPoint j) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        Signature s = j.getSignature();
        Class<?>[] paramClazz = getParamClass(s);
        Class<?> clazz = s.getDeclaringType();
        return clazz.getMethod(s.getName(), paramClazz);
    }

    private static Class<?>[] getParamClass(Signature s) throws ClassNotFoundException {
        Matcher m = REGPATTERN.matcher(s.toLongString());
        String[] classNames = null;
        if (m.find()) {
            classNames = m.group().split(",");
        }

        Class[] paramClass;
        if (classNames != null) {
            paramClass = new Class[classNames.length];

            for(int i = 0; i < paramClass.length; ++i) {
                paramClass[i] = getClass(classNames[i]);
            }
        } else {
            paramClass = new Class[0];
        }

        return paramClass;
    }

    private static Class<?> getClass(String className) throws ClassNotFoundException {
        Class<?> clazz = null;
        Class<?>[] classes = new Class[]{Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Character.TYPE, Boolean.TYPE, Float.TYPE, Double.TYPE};
        int e = classes.length;

        for(int var5 = 0; var5 < e; ++var5) {
            Class<?> c = classes[var5];
            if (className.equals(c.toString())) {
                clazz = c;
                break;
            }
        }

        if (className.indexOf("[]") > 0) {
            int s = className.indexOf("[]");
            e = className.lastIndexOf("[]");
            clazz = Class.forName(className.replace("[]", ""));
            Object arr = Array.newInstance(clazz, new int[(e - s) / 2 + 1]);
            clazz = arr.getClass();
        }

        if (clazz == null) {
            clazz = Class.forName(className);
        }

        return clazz;
    }

    /**
     * 获取指定域值
     *
     * @param obj       the obj
     * @param fieldName the field name
     * @return the field value
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);

            try {
                result = field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException var5) {
                var5.printStackTrace();
            }
        }

        return result;
    }

    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        Class clazz = obj.getClass();

        while(clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException var5) {
                clazz = clazz.getSuperclass();
            }
        }

        return field;
    }

    /**
     * 给指定域赋值
     *
     * @param obj        the obj
     * @param fieldName  the field name
     * @param fieldValue the field value
     */
    public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
        Field field = getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException | IllegalAccessException var5) {
                var5.printStackTrace();
            }
        }

    }
}

