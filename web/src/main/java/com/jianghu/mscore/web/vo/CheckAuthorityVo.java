package com.jianghu.mscore.web.vo;

import javax.validation.constraints.NotNull;

public class CheckAuthorityVo {

    @NotNull
    private Integer staffId;

    @NotNull
    private String url;

    public CheckAuthorityVo(@NotNull Integer staffId, @NotNull String url) {
        this.staffId = staffId;
        this.url = url;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
