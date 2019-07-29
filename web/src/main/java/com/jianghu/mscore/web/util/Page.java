package com.jianghu.mscore.web.util;

import java.io.Serializable;

/**
 * 分页
 *
 * @author huj
 * @version 1.0
 * @since 2017.03.27
 */
public class Page implements Serializable {

    /**
     *  当前第几页(默认第一页),---主要用于传递到前台显示
     */
    private int currentPageNum = 1;

    /**
     * 总页数
     */
    private int totalPageNum;

    /**
     * 总记录数
     */
    private int totalCount;



    /**
     * 每页显示的记录条数(默认10条)
     */
    private int perPageSize = 5;

    public Page() {
    }

    public Page(int currentPageNum, int perPageSize) {
        this.currentPageNum = currentPageNum;
        this.perPageSize = perPageSize;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPerPageSize() {
        return perPageSize;
    }

    public void setPerPageSize(int perPageSize) {
        this.perPageSize = perPageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPageNum=" + currentPageNum +
                ", totalPageNum=" + totalPageNum +
                ", totalCount=" + totalCount +
                ", perPageSize=" + perPageSize +
                '}';
    }
}

