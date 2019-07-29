package com.jianghu.mscore.api;

import java.io.Serializable;

public class PageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int currentPageNum = 1;
    protected int perPageSize = 5;
    protected int totalPageNum;
    protected int totalCount;

    public PageVo() {
    }

    public PageVo(Integer currentPageNum, Integer perPageSize) {
        this.currentPageNum = currentPageNum;
        this.perPageSize = perPageSize;
    }

    public int getCurrentPageNum() {
        return this.currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getPerPageSize() {
        return this.perPageSize;
    }

    public void setPerPageSize(Integer perPageSize) {
        this.perPageSize = perPageSize;
    }

    public int getTotalPageNum() {
        return this.totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
