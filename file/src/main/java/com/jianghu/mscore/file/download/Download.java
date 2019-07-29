package com.jianghu.mscore.file.download;

import com.jianghu.mscore.file.Loadable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 下载接口
 *
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
public interface Download extends Loadable {

    /**
     * 下载文件
     *
     * @param response the response
     * @param helper   the helper
     * @throws IOException the io exception
     * @since 2019.06.11
     */
    void download(HttpServletResponse response, DownloadHelper helper) throws IOException;

}
