package com.jianghu.mscore.context.data.base;

import java.util.List;

/**
 * 定义Mybatis的通用操作
 *
 * @param <T> the type parameter
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public interface InterfaceCrudMapper<T> extends InterfaceMapper<T>{

    /**
     * Select by primary key t.
     *
     * @param id the id
     * @return the t
     * @since 2019.04.24
     */
    T selectByPrimaryKey(String id);


    /**
     * Delete by primary key int.
     *
     * @param id the id
     * @return the int
     * @since 2019.04.24
     */
    int deleteByPrimaryKey(String id);


    /**
     * Insert selective int.
     *
     * @param t the t
     * @return the int
     * @since 2019.04.24
     */
    int insertSelective(T t);


    /**
     * Update by primary key selective int.
     *
     * @param t the t
     * @return the int
     * @since 2019.04.24
     */
    int updateByPrimaryKeySelective(T t);


    /**
     * Select by selective list.
     *
     * @param t the t
     * @return the list
     * @since 2019.04.24
     */
    List<T> selectBySelective(T t);


}

