package com.jianghu.mscore.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * 装配工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.20
 */
public class Assembler {
    private static final Logger logger = LoggerFactory.getLogger(Assembler.class);


    /**
     * 对象属性装配
     *
     * @param fromObject the from object
     * @param toObject   the to object
     * @since 2019.06.20
     */
    public static void assemble(Object fromObject, Object toObject) {
        BeanUtils.copyProperties(fromObject, toObject);
    }

    /**
     * 对象属性装配，指定忽略属性
     *
     * @param fromObject       the from object
     * @param toObject         the to object
     * @param ignoreProperties the ignore properties
     * @since 2019.06.20
     */
    public static void assemble(Object fromObject, Object toObject, String... ignoreProperties) {
        BeanUtils.copyProperties(fromObject, toObject, ignoreProperties);
    }

    /**
     * 列表属性装配，指定忽略属性
     *
     * @param <T>           the type parameter
     * @param fromList      the from list
     * @param toClass       the to class
     * @param excludeFields the exclude fields
     * @return the list
     * @since 2019.06.20
     */
    public static <T> List<T> assembleList2NewList(List<?> fromList, Class<T> toClass, String... excludeFields) {
        ArrayList toList = new ArrayList(fromList.size());

        Object toObject;
        try {
            for(Iterator iterator = fromList.iterator(); iterator.hasNext(); toList.add(toObject)) {
                Object fromObject = iterator.next();
                toObject = toClass.newInstance();
                if (excludeFields.length > 0) {
                    assemble(fromObject, toObject, excludeFields);
                } else {
                    assemble(fromObject, toObject);
                }
            }
        } catch (Exception var7) {
            logger.error("assember error", var7);
        }

        return toList;
    }

    /**
     * 列表属性装配，指定忽略属性，指定类型
     *
     * @param <T>           the type parameter
     * @param fromList      the from list
     * @param toList        the to list
     * @param toClass       the to class
     * @param excludeFields the exclude fields
     * @return the list
     * @since 2019.06.20
     */
    public static <T> List<T> assembleList2List(List<?> fromList, List<T> toList, Class<T> toClass, String... excludeFields) {
        try {
            for(int i = 0; i < fromList.size(); ++i) {
                Object fromObject = fromList.get(i);
                T toObject = i >= toList.size() ? toClass.newInstance() : toList.get(i);
                if (excludeFields.length > 0) {
                    assemble(fromObject, toObject, excludeFields);
                } else {
                    assemble(fromObject, toObject);
                }

                toList.add(toObject);
            }
        } catch (Exception var7) {
            logger.error("assember error", var7);
        }

        return toList;
    }
}

