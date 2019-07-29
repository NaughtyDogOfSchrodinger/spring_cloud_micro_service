package com.jianghu.mscore.web.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jianghu.mscore.web.config.AppThreadCache;
import com.jianghu.mscore.web.config.properties.ApplicationProperties;
import com.jianghu.mscore.web.constant.PlatformEnum;
import com.jianghu.mscore.web.util.JsonTools;
import com.jianghu.mscore.web.vo.PageVo;
import com.jianghu.mscore.web.vo.ResultVo;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务层基类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
@EnableConfigurationProperties({
        ApplicationProperties.class,
})
public abstract class AbstractService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Resource
    private ApplicationProperties applicationProperties;


    /**
     * Gets ss id key.
     *
     * @param ssid the ssid
     * @return the ss id key
     */
    protected String getSsIdKey(String ssid) {
        return getSessionIdKey() + ":" + ssid;
    }


    /**
     * 获取 session id.
     *
     * @return the session id
     */
    public String getSessionId() {
        return AppThreadCache.getSessionId();
    }


    /**
     * 获取 session id 键.
     *
     * @return the session id key
     */
    public String getSessionIdKey() {
        return applicationProperties.getSessionKey();
    }


    /**
     * 获取 x forwarded for(客户端ip).
     *
     * @return the x forwarded for
     */
    protected String getXForwardedFor() {
        return AppThreadCache.getXForwardedFor();
    }

    /**
     * 获取授权配置
     *
     * @return the application properties
     */
    public ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }

    /**
     * 获取终端
     *
     * @return the platform
     */
    public PlatformEnum getPlatform() {
        return AppThreadCache.getPlatform();
    }

    /**
     * 获取app域名
     *
     * @return the server domain
     */
    public String getServerDomain() {
        return applicationProperties.getDomain();
    }

    /**
     * 对象转json字符串
     *
     * @param o the o
     * @return the string
     * @since 2019.01.22
     */
    protected String buildJsonString(Object o) {
        return JSON.toJSONString(o);
    }

    /**
     * 获取用户报头
     *
     * @return the user agent
     */
    protected String getUserAgent() {
        return AppThreadCache.getRequestHeader("user-agent");
    }


    /**
     * 添加分页信息
     *
     * @param <T>    the type parameter
     * @param list   the list
     * @param pageVo the page vo
     * @since 2019.01.24
     */
    protected <T> void addPageInfo(List<T> list, PageVo pageVo) {
        PageInfo<T> info = new PageInfo<>(list);
        pageVo.setTotalCount(NumberUtils.toInt(info.getTotal() + ""));
        pageVo.setTotalPageNum(info.getPages());
    }

    protected List<Map<String, Object>> getListWithPage(PageVo pageVo, ResultVo resultVo) {
        List<Map<String, Object>> list = JsonTools.parseJSON2List(resultVo.getData());
        PageVo page = JsonTools.parse2Page(resultVo.getPage());
        com.jianghu.mscore.web.core.util.Assembler.assemble(page, pageVo);
        return list;
    }

    /**
     * 获取rest template
     *
     * @return the rest template
     */
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void threadLocalRemove() {
        AppThreadCache.remove();
    }
}
