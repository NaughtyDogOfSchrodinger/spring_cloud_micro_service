package com.jianghu.mscore.file.download;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 下载工具
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
@FunctionalInterface
public interface DownloadHelper {

    /**
     * 经流写入响应体
     *
     * @param response     the response
     * @param outputStream the output stream
     * @throws IOException the io exception
     * @since 2019.06.11
     */
    void writeStream(HttpServletResponse response, OutputStream outputStream) throws IOException;
}
