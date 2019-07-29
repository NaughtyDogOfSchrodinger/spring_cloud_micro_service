package com.jianghu.mscore.context.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 获取配置文件中spring.application.service节点值
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
@Service
public class RemoteServiceConfigurer {

    @Autowired
    private ApplicationConfigurer applicationConfigurer;
    /**
     * Gets service node.
     *
     * @param nodeCode the node code
     * @return the service node
     */
    public Map<String, Object> getServiceNode(String nodeCode) {
        if(getRemoteServiceConfigNode()!=null){
            return  (Map<String, Object>) getRemoteServiceConfigNode().get(nodeCode);
        }
        else{
            return null;
        }
    }

    /**
     * Get remote service config node map.
     *
     * @return the map
     * @since 2019.04.24
     */
    public  Map<String, Object> getRemoteServiceConfigNode(){
        Map<String, Object> application = (Map<String, Object>) applicationConfigurer.getSpring().get("application");
        Map<String, Object> errorConfigNode=(Map<String, Object>) application.get("service");
        return errorConfigNode;
    }
}

