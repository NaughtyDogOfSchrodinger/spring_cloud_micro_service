package com.jianghu.mscore.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jianghu.mscore.exception.WebException;
import com.jianghu.mscore.web.vo.ResultVo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * http工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.20
 */
public final class HttpUtil {

    public static ResultVo HttpRequest(String requestUrl, String requestMethod, HttpServletRequest request) {
        return HttpRequest(requestUrl, requestMethod, null, request);
    }



    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param output        提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static ResultVo HttpRequest(String requestUrl, String requestMethod,  String output, HttpServletRequest request) {
        ResultVo resultVo = new ResultVo();
        try {
            StringBuffer buffer = new StringBuffer();
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = buildConnection(url, requestMethod, request);
            if (connection == null) {
                throw new WebException("创建连接异常!");
            }
            // 鉴权
            if (request != null) {
                addAuthorization(request, connection);
            }
            // 添加参数
            if (StringUtils.isNotBlank(output)) {
                OutputStream out = connection.getOutputStream();
                out.write(output.getBytes(StandardCharsets.UTF_8));
                out.close();
            }
            // 返回值
            InputStream input = connection.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputReader);
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            //获取cookie
            String auth = getAuthorization(connection);
            if (StringUtils.isNotBlank(auth)){
                resultVo.setCookie(auth);
            }

            // 关闭连接、释放资源
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputReader);
            IOUtils.closeQuietly(input);
            connection.disconnect();
            // 处理返回值
            JSONObject jsonObject = JSON.parseObject(buffer.toString());
            handleResult(jsonObject, resultVo);
        } catch (Exception e) {
            // 请求异常
            throw new WebException("请求 " + requestUrl + " 发生未知异常 " + e.getMessage() + ",请联系管理员");
        }
        // 业务异常
        if (resultVo.getStatus().equals(NumberUtils.INTEGER_ZERO)) {
            throw new WebException(resultVo.getMsg());
        }
        return resultVo;
    }

    private static String getAuthorization(HttpURLConnection connection) {
        Map<String, List<String>> map = connection.getHeaderFields();
        String auth = MapUtils.getString(map, "Set-Cookie");
        if (StringUtils.isNotBlank(auth)) {
            return auth.replace("[", "").replace("]", "");
        }
        return StringUtils.EMPTY;
    }

    private static void addAuthorization(HttpServletRequest request, HttpURLConnection connection) {
        connection.setRequestProperty("cs-admin", UUID.randomUUID().toString());
    }

    private static HttpURLConnection buildConnection(URL url, String requestMethod, HttpServletRequest request) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            // 请求头 自定义
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setRequestMethod(requestMethod);
            return connection;
        } catch (Exception e) {
            return null;
        }
    }

    private static void handleResult(JSONObject jsonObject, ResultVo resultVo) {
        resultVo.setStatus(NumberUtils.toInt(jsonObject.get("status") + ""));
        resultVo.setMsg(jsonObject.get("msg") + "");
        resultVo.setData(jsonObject.containsKey("data") ? jsonObject.get("data") + "" : "");
        resultVo.setPage(jsonObject.containsKey("page") ? jsonObject.get("page") + "" : "");
        if (!"".equals(resultVo.getPage())&&null!=resultVo.getPage()){
            resultVo.setPageVo(JsonTools.parse2Page(resultVo.getPage()));
        }
    }

    /**
     * 发送json
     *
     * @param response the response
     * @param obj      the obj
     * @throws Exception the exception
     * @since 2018.12.20
     */
    public static void sendJson(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    }

}
