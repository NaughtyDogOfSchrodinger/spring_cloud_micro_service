package com.jianghu.mscore.web.core.util;


import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 对象属性装配
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.01.21
 */
public class Assembler {

    /**
     * 装配对象，所有属性
     *
     * @param fromObject the from object
     * @param toObject   the to object
     * @since 2019.01.21
     */
    public static void assemble(Object fromObject, Object toObject) {
        BeanUtils.copyProperties(fromObject, toObject);
    }

    /**
     * 装配对象，指定属性
     *
     * @param fromObject       the from object
     * @param toObject         the to object
     * @param ignoreProperties the ignore properties
     * @since 2019.01.21
     */
    public static void assemble(Object fromObject, Object toObject, String... ignoreProperties) {
        BeanUtils.copyProperties(fromObject, toObject, ignoreProperties);
    }

    /**
     * 装配list
     *
     * @param <T>           the type parameter
     * @param fromList      the from list
     * @param toClass       the to class
     * @param excludeFields the exclude fields
     * @return the array list
     * @since 2019.01.21
     */
    public static <T> ArrayList assembleList2NewList(List<?> fromList, Class<T> toClass, String... excludeFields) {
        ArrayList toList = new ArrayList(fromList.size());

        Object toObject;
        try {
            for (Iterator iterator = fromList.iterator(); iterator.hasNext(); toList.add(toObject)) {
                Object fromObject = iterator.next();
                toObject = toClass.newInstance();
                if (excludeFields.length > 0) {
                    assemble(fromObject, toObject, excludeFields);
                } else {
                    assemble(fromObject, toObject);
                }
            }
        } catch (Exception ignored) {
        }

        return toList;
    }

    /**
     * 装配list，指定属性
     *
     * @param <T>           the type parameter
     * @param fromList      the from list
     * @param toList        the to list
     * @param toClass       the to class
     * @param excludeFields the exclude fields
     * @return the list
     * @since 2019.01.21
     */
    public static <T> List<T> assembleList2List(List<?> fromList, List<T> toList, Class<T> toClass, String... excludeFields) {
        try {
            for (int i = 0; i < fromList.size(); ++i) {
                Object fromObject = fromList.get(i);
                T toObject = i >= toList.size() ? toClass.newInstance() : toList.get(i);
                if (excludeFields.length > 0) {
                    assemble(fromObject, toObject, excludeFields);
                } else {
                    assemble(fromObject, toObject);
                }

                toList.add(toObject);
            }
        } catch (Exception ignored) {
        }

        return toList;
    }
}
