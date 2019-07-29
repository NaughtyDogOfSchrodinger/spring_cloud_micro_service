package com.jianghu.mscore.context.config;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 获取配置文件中错误节点
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.24
 */
@Service
public class ErrorConfigurer {

    @Resource
    private ApplicationConfigurer applicationConfigurer;

    /**
     * Gets error msg.
     *
     * @param nodeCode the node code
     * @return the error msg
     */
    public String getErrorMsg(String nodeCode) {
        Map<String, Object> errorNode = getErrorNode(nodeCode);
        return (String) errorNode.get("msg");
    }

    /**
     * Gets error code.
     *
     * @param nodeCode the node code
     * @return the error code
     */
    public String getErrorCode(String nodeCode) {
        Map<String, Object> errorNode = getErrorNode(nodeCode);
        return (String) errorNode.get("code");
    }

    /**
     * Gets error node.
     *
     * @param nodeCode the node code
     * @return the error node
     */
    public Map<String, Object> getErrorNode(String nodeCode) {
        if(getErrorConfigNode()!=null){
            return  (Map<String, Object>) getErrorConfigNode().get(nodeCode);
        }
        else{
            return null;
        }
    }

    /**
     * Get error config node map.
     *
     * @return the map
     * @since 2019.04.24
     */
    public  Map<String, Object> getErrorConfigNode(){
        Map<String, Object> application = (Map<String, Object>) applicationConfigurer.getSpring().get("application");
        Map<String, Object> errorConfigNode=(Map<String, Object>) application.get("error");
        return errorConfigNode;
    }
}

