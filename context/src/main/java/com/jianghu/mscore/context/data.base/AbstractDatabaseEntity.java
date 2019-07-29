package com.jianghu.mscore.context.data.base;

import java.io.Serializable;

/**
 * 定义实体基础类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
public abstract class AbstractDatabaseEntity implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }
}
