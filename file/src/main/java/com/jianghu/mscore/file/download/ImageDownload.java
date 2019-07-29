package com.jianghu.mscore.file.download;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ImageDownload extends AbstractDownload {
    @Override
    protected HttpServletResponse addHeader(HttpServletResponse response) {
        //普通IO流实现下载的功能
        response.setContentType("image/*"); //设置内容类型
        response.setHeader("Content-disposition", "attachment;filename=" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png");//设置下载的文件名称
        return response;
    }
}
