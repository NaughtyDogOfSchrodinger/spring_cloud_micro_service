package com.jianghu.mscore.file.download;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ExcelDownload extends AbstractDownload {
    @Override
    protected HttpServletResponse addHeader(HttpServletResponse response) {
        // 设置头
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls");
        return response;
    }

}
