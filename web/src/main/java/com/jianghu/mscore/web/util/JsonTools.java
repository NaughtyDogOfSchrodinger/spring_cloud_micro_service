package com.jianghu.mscore.web.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jianghu.mscore.web.vo.PageVo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.01.21
 */
public class JsonTools {

    /**
     * Parse json 2 list list.
     *
     * @param jsonStr the json str
     * @return the list
     * @since 2019.01.21
     */
    public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
        JSONArray jsonArr = JSONArray.parseArray(jsonStr);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Object o : jsonArr) {
            JSONObject json2 = (JSONObject) o;
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }

    /**
     * Parse 2 page page vo.
     *
     * @param jsonStr the json str
     * @return the page vo
     * @since 2019.01.21
     */
    public static PageVo parse2Page(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return new PageVo();
        }
        PageVo pageVo = new PageVo();
        Map<String, Object> data = parseJSON2Map(jsonStr);
        pageVo.setTotalCount(MapUtils.getInteger(data,"totalCount"));
        pageVo.setTotalPageNum(MapUtils.getInteger(data,"totalPageNum"));
        pageVo.setPerPageSize(MapUtils.getInteger(data,"perPageSize"));
        pageVo.setCurrentPageNum(MapUtils.getInteger(data,"currentPageNum"));
        return pageVo;
    }

    /**
     * Parse json 2 map map.
     *
     * @param jsonStr the json str
     * @return the map
     * @since 2019.01.21
     */
    public static Map<String, Object> parseJSON2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.parseObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                ArrayList list = new ArrayList();
                for (Object json2 : ((JSONArray) v)) {
                    if (json2 instanceof JSONArray) {
                        list.add(parseJSON2List(json2.toString()));
                    } else {
                        list.add(json2);
                    }

                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * Parse json object.
     *
     * @param jsonStr the json str
     * @return the json object
     * @since 2019.01.21
     */
    public static JSONObject parse(String jsonStr){
        return JSONObject.parseObject(jsonStr);
    }


    /**
     * Gets list by url.
     *
     * @param url the url
     * @return the list by url
     */
    public static List<Map<String, Object>> getListByUrl(String url) {
        try {
            //通过HTTP获取JSON数据
            InputStream in = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return parseJSON2List(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
