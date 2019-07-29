package com.jianghu.mscore.core.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 权限一键同步传值实体
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.01.26
 */
@Component
public class SectionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${authority.menu.name:null}")
    private String menuName;

    @Value("${authority.menu.detail:null}")
    private String menuDetail;

    @Value("${authority.menu.showOrder:-1}")
    private Integer showOrder;

    @Value("${authority.menu.url:null}")
    private String url;

    private Set<String> urlPrefix;

    private List<Section> sectionList;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDetail() {
        return menuDetail;
    }

    public void setMenuDetail(String menuDetail) {
        this.menuDetail = menuDetail;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getUrlPrefix() {
        return urlPrefix;
    }

    public SectionVo setUrlPrefix(Set<String> urlPrefix) {
        this.urlPrefix = urlPrefix;
        return this;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    /**
     * The type Page.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.01.26
     */
    public static class Section implements Serializable{

        private static final long serialVersionUID = 1L;

        private String sectionName;

        private String sectionDetail;

        private Integer showOrder;

        /**
         * system_permissions.controller
         * controller包名全路径
         */
        private String controller;

        private String sectionUrl;

        private List<Authority> authorityList = new ArrayList<>();

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getSectionDetail() {
            return sectionDetail;
        }

        public void setSectionDetail(String sectionDetail) {
            this.sectionDetail = sectionDetail;
        }

        public Integer getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(Integer showOrder) {
            this.showOrder = showOrder;
        }

        public String getController() {
            return controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }

        public String getSectionUrl() {
            return sectionUrl;
        }

        public void setSectionUrl(String sectionUrl) {
            this.sectionUrl = sectionUrl;
        }

        public List<Authority> getAuthorityList() {
            return authorityList;
        }

        public void setAuthorityList(List<Authority> authorityList) {
            this.authorityList = authorityList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Section section = (Section) o;
            return sectionName.equals(section.sectionName) &&
                    showOrder.equals(section.showOrder) &&
                    controller.equals(section.controller);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sectionName, showOrder, controller);
        }
    }

    /**
     * The type Authority.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.01.26
     */
    public static class Authority implements Serializable{

        private static final long serialVersionUID = 1L;

        /**
         * system_permissions.permission_name
         * 权限名称（功能名称）
         */
        private String permissionName;


        private String permissionDetail;

        /**
         * system_permissions.actionKey
         * actionkey（url）
         */
        private String actionKey;

        public String getPermissionName() {
            return permissionName;
        }

        public void setPermissionName(String permissionName) {
            this.permissionName = permissionName;
        }

        public String getPermissionDetail() {
            return permissionDetail;
        }

        public void setPermissionDetail(String permissionDetail) {
            this.permissionDetail = permissionDetail;
        }

        public String getActionKey() {
            return actionKey;
        }

        public void setActionKey(String actionKey) {
            this.actionKey = actionKey;
        }
    }

}



