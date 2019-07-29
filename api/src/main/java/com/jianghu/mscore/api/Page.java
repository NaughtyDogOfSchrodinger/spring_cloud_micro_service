package com.jianghu.mscore.api;

import java.io.Serializable;

/**
 * 分页
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.23
 */
public class Page implements Serializable {

    private int currentPageNum = 1;
    private int totalPageNum;
    private int totalCount;
    private int perPageSize = 5;

    public Page() {
    }

    public Page(int currentPageNum, int perPageSize) {
        this.currentPageNum = currentPageNum;
        this.perPageSize = perPageSize;
    }

    public int getCurrentPageNum() {
        return this.currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return this.totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPerPageSize() {
        return this.perPageSize;
    }

    public void setPerPageSize(int perPageSize) {
        this.perPageSize = perPageSize;
    }

    public String toString() {
        return "Page{currentPageNum=" + this.currentPageNum + ", totalPageNum=" + this.totalPageNum + ", totalCount=" + this.totalCount + ", perPageSize=" + this.perPageSize + '}';
    }
}

