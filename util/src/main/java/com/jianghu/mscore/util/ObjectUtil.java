package com.jianghu.mscore.util;

import com.google.common.collect.Maps;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUtil {
    private static final Logger log = LoggerFactory.getLogger(ObjectUtil.class);

    public ObjectUtil() {
    }

    public static void setDefault(Object obj) {
        try {
            Field[] var1 = obj.getClass().getDeclaredFields();
            int var2 = var1.length;

            for (Field field : var1) {
                field.setAccessible(true);
                Object object = field.get(obj);
                if (!StringUtils.equals("id", field.getName()) && object == null) {
                    setDefault(field, obj);
                }
            }
        } catch (Exception var6) {
            log.error(var6.getMessage(), var6);
        }

    }

    public static void setDefaultExcludeFields(Object obj, String... excludeFields) {
        try {
            List<String> fieldNameList = Arrays.asList(excludeFields);
            Field[] var3 = obj.getClass().getDeclaredFields();
            int var4 = var3.length;

            for (Field field : var3) {
                field.setAccessible(true);
                Object object = field.get(obj);
                if (!StringUtils.equals("id", field.getName()) && !fieldNameList.contains(field.getName()) && object == null) {
                    setDefault(field, obj);
                }
            }
        } catch (Exception var8) {
            log.error(var8.getMessage(), var8);
        }

    }

    private static void setDefault(Field field, Object obj) throws IllegalAccessException {
        if (field.getType().equals(String.class)) {
            field.set(obj, "");
        }

        if (field.getType().equals(Long.class)) {
            field.set(obj, 0L);
        }

        if (field.getType().equals(Integer.class)) {
            field.set(obj, 0);
        }

        if (field.getType().equals(Byte.class)) {
            field.set(obj, (byte)0);
        }

        if (field.getType().equals(Boolean.class)) {
            field.set(obj, false);
        }

        if (field.getType().equals(Date.class)) {
            field.set(obj, new Date());
        }

        if (field.getType().equals(Float.class)) {
            field.set(obj, 0.0F);
        }

        if (field.getType().equals(BigDecimal.class)) {
            field.set(obj, BigDecimal.ZERO);
        }

        if (field.getType().equals(Double.class)) {
            field.set(obj, 0.0D);
        }

        if (field.getType().equals(Timestamp.class)) {
            field.set(obj, new Timestamp(0L));
        }

    }

    public static String toString(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            return "null";
        } else {
            try {
                if (!obj.getClass().isPrimitive() && !(obj instanceof String) && !(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Boolean)) {
                    if (obj instanceof Map) {
                        Map map = (Map)obj;
                        return toString(map);
                    } else if (obj instanceof Collection) {
                        Collection cls = (Collection)obj;
                        return toString(cls);
                    } else {
                        int var3;
                        int var4;
                        if (obj instanceof Object[]) {
                            Object[] var2 = (Object[])obj;
                            var3 = var2.length;

                            for(var4 = 0; var4 < var3; ++var4) {
                                Object o = var2[var4];
                                if (!o.getClass().isPrimitive() && !(o instanceof String) && !(o instanceof Integer) && !(o instanceof Long) && !(o instanceof Boolean)) {
                                    sb.append(toString(o)).append(",");
                                } else {
                                    sb.append(o).append(",");
                                }
                            }
                        }

                        Field[] var8 = obj.getClass().getDeclaredFields();
                        var3 = var8.length;

                        for(var4 = 0; var4 < var3; ++var4) {
                            Field field = var8[var4];
                            field.setAccessible(true);
                            Object object = field.get(obj);
                            if (object != null) {
                                sb.append("\"").append(field.getName()).append("\":");
                                if (object instanceof String) {
                                    sb.append("\"").append(object).append("\",");
                                } else {
                                    sb.append(object).append(",");
                                }
                            }
                        }

                        return sb.length() == 0 ? "{}" : "{" + sb.substring(0, sb.length() - 1) + "}";
                    }
                } else {
                    return obj.toString();
                }
            } catch (Exception var7) {
                log.error("ObjectUtil toString error,{},{}", obj, var7);
                return obj.toString();
            }
        }
    }

    public static String toString(Collection objs) {
        StringBuilder sb = new StringBuilder();
        if (objs == null) {
            return "null";
        } else {
            Iterator var2 = objs.iterator();

            while(var2.hasNext()) {
                Object obj = var2.next();
                if (obj.getClass().isPrimitive()) {
                    sb.append(obj).append(",");
                } else {
                    sb.append(toString(obj)).append(",");
                }
            }

            return sb.length() == 0 ? "[]" : "[" + sb.substring(0, sb.length() - 1) + "]";
        }
    }

    public static boolean hasNonNullProperty(Object obj) {
        try {
            Field[] var1 = obj.getClass().getDeclaredFields();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Field field = var1[var3];
                field.setAccessible(true);
                Object property = field.get(obj);
                if (property != null) {
                    return true;
                }
            }
        } catch (Exception var6) {
            log.warn(var6.getMessage(), var6);
        }

        return false;
    }

    public static String toString(Map map) {
        StringBuilder sb = new StringBuilder();
        if (map == null) {
            return "null";
        } else {
            Iterator var2 = map.entrySet().iterator();

            while(var2.hasNext()) {
                Object key = var2.next();
                Object value = map.get(key);
                sb.append("\"").append(toString(key)).append("\":").append(toString(value)).append(",");
            }

            return sb.length() == 0 ? "{}" : "{" + sb.substring(0, sb.length() - 1) + "}";
        }
    }

    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        } else {
            HashMap map = Maps.newHashMap();

            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var4 = propertyDescriptors;
                int var5 = propertyDescriptors.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor property = var4[var6];
                    String key = property.getName();
                    if (!StringUtils.equals("class", key)) {
                        Method getter = property.getReadMethod();
                        Object value = getter.invoke(obj);
                        map.put(key, value);
                    }
                }
            } catch (Exception var11) {
                log.error("transBean2Map Error " + var11);
            }

            return map;
        }
    }

    public static boolean isNullObj(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Collection && ((Collection)obj).size() == 0) {
            return true;
        } else if (obj instanceof Map && ((Map)obj).keySet().size() == 0) {
            return true;
        } else {
            return obj.getClass().isArray() && ((Object[])((Object[])obj)).length == 0;
        }
    }

    public static boolean isSame(Object source, Object target) {
        try {
            Class<?> clazz1 = source.getClass();
            Class<?> clazz2 = target.getClass();
            if (!clazz1.getSimpleName().equals(clazz2.getSimpleName())) {
                return false;
            } else {
                Field[] field1 = clazz1.getDeclaredFields();
                Field[] field2 = clazz2.getDeclaredFields();
                if (field1.length != field2.length) {
                    return false;
                } else {
                    Field[] var6 = field1;
                    int var7 = field1.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Field aField1 = var6[var8];
                        Field[] var10 = field2;
                        int var11 = field2.length;

                        for(int var12 = 0; var12 < var11; ++var12) {
                            Field aField2 = var10[var12];
                            if (aField1.getName().equals(aField2.getName())) {
                                aField1.setAccessible(true);
                                aField2.setAccessible(true);
                                if (!isSameObject(aField1.get(source), aField2.get(target))) {
                                    return false;
                                }
                            }
                        }
                    }

                    return true;
                }
            }
        } catch (Exception var14) {
            log.warn(var14.getMessage(), var14);
            return false;
        }
    }

    public static boolean isSameObject(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else {
            return obj1 != null && obj2 != null && obj1.equals(obj2);
        }
    }
}

