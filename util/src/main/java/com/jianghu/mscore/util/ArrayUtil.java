package com.jianghu.mscore.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.20
 */
public final class ArrayUtil {
    /**
     * The type Array util.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.06.20
     */
    public ArrayUtil() {
    }

    /**
     * int数组求和
     *
     * @param arr the arr
     * @return the sum
     */
    public static Integer getSum(Integer[] arr) {
        int sum = 0;
        if (arr != null && arr.length >= 1) {
            Integer[] var2 = arr;
            int var3 = arr.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Integer num = var2[var4];
                sum += num;
            }

            return sum;
        } else {
            return sum;
        }
    }

    /**
     * Gets unique sql string.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the unique sql string
     */
    public static <T> String getUniqueSqlString(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            List<Object> idList = Lists.newArrayList();
            StringBuilder buf = new StringBuilder();

            for (Object id : list) {
                if (idList.indexOf(id) < 0) {
                    buf.append(",\"").append(id).append("\"");
                    idList.add(id);
                }
            }

            String bufs = " (" + buf.toString().substring(1) + ") ";
            return bufs;
        }
    }

    /**
     * Gets unique sql string.
     *
     * @param arr the arr
     * @return the unique sql string
     */
    public static String getUniqueSqlString(Object[] arr) {
        if (arr != null && !ArrayUtils.isEmpty(arr)) {
            List<Object> idList = Lists.newArrayList();
            StringBuilder buf = new StringBuilder();

            for (Object id : arr) {
                if (idList.indexOf(id) < 0) {
                    buf.append(",\"").append(id).append("\"");
                    idList.add(id);
                }
            }

            return " (" + buf.toString().substring(1) + ") ";
        } else {
            return null;
        }
    }

    /**
     * 获取List中public域的集合
     *
     * @param <T>       the type parameter
     * @param elements  the elements
     * @param fieldName the field name
     * @return the declared field value list
     * @throws SecurityException        the security exception
     * @throws NoSuchFieldException     the no such field exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException   the illegal access exception
     */
    public static <T> List<Object> getDeclaredFieldValueList(List<T> elements, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        } else {
            List<Object> list = Lists.newArrayList();

            for (T e : elements) {
                Class ownerClass = e.getClass();
                Field field = ownerClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object property = field.get(e);
                list.add(property);
            }

            return list;
        }
    }

    /**
     * Gets field sql string.
     *
     * @param <T>       the type parameter
     * @param elements  the elements
     * @param fieldName the field name
     * @return the field sql string
     * @throws SecurityException        the security exception
     * @throws NoSuchFieldException     the no such field exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException   the illegal access exception
     */
    public static <T> String getFieldSqlString(List<T> elements, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        List<Object> objectList = getFieldValueList(elements, fieldName);
        return getUniqueSqlString(objectList);
    }

    /**
     * Gets field value list.
     *
     * @param <T>       the type parameter
     * @param elements  the elements
     * @param fieldName the field name
     * @return the field value list
     * @throws SecurityException        the security exception
     * @throws NoSuchFieldException     the no such field exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException   the illegal access exception
     */
    public static <T> List<Object> getFieldValueList(List<T> elements, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        } else {
            List<Object> list = Lists.newArrayList();

            for (T e : elements) {
                Class ownerClass = e.getClass();
                Field field = ownerClass.getField(fieldName);
                Object property = field.get(e);
                list.add(property);
            }

            return list;
        }
    }

    /**
     * Gets uniq list.
     *
     * @param arr the arr
     * @return the uniq list
     */
    public static List<String> getUniqList(String[] arr) {
        if (arr != null && arr.length >= 1) {
            List<String> list = Lists.newArrayList();
            Set<String> set = Sets.newHashSet();
            int var4 = arr.length;

            for (String e : arr) {
                if (e.length() > 0) {
                    set.add(e);
                }
            }

            list.addAll(set);
            return list;
        } else {
            return null;
        }
    }

    /**
     * List 2 uniq str string.
     *
     * @param <T>   the type parameter
     * @param list  the list
     * @param split the split
     * @return the string
     * @since 2019.06.20
     */
    public static <T> String list2UniqStr(List<T> list, String split) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            Set<String> set = Sets.newHashSet();
            set.addAll(list.stream().filter((ex) -> ex != null).map(Object::toString).collect(Collectors.toList()));
            StringBuilder buf = new StringBuilder();

            for (String e : set) {
                buf.append(split).append(e);
            }

            return buf.substring(split.length());
        }
    }
}

