package com.jianghu.mscore.web.vo;

public class PageVo {

    protected int currentPageNum = 1;// 当前第几页(默认第一页),---主要用于传递到前台显示
    protected int perPageSize = 5;// 每页显示的记录条数(默认10条)
    protected int totalPageNum;// 总页数
    protected int totalCount;// 总记录数

    public PageVo() {
    }

    public PageVo(Integer currentPageNum, Integer perPageSize) {

        this.currentPageNum = currentPageNum;
        this.perPageSize = perPageSize;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getPerPageSize() {
        return perPageSize;
    }

    public void setPerPageSize(Integer perPageSize) {
        this.perPageSize = perPageSize;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
