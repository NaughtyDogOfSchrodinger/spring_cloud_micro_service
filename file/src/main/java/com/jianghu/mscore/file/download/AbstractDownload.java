package com.jianghu.mscore.file.download;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对下载接口{@link Download#download(HttpServletResponse, DownloadHelper)}方法做基本实现
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
public abstract class AbstractDownload implements Download {

    /**
     * Add header http servlet response.
     *
     * @param response the response
     * @return the http servlet response
     * @since 2019.06.11
     */
    protected abstract HttpServletResponse addHeader(HttpServletResponse response);

    /**
     * 添加对应请求头后，将流写入响应体
     *
     * @param response the response
     * @param helper   the helper
     * @throws IOException
     */
    public void download(HttpServletResponse response, DownloadHelper helper) throws IOException {
        helper.writeStream(addHeader(response), response.getOutputStream());
    }

}
