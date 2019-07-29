package com.jianghu.mscore.file;

import com.jianghu.mscore.file.excel.ExcelTemplate;
import com.jianghu.mscore.file.util.FileTypeUtil;
import com.jianghu.mscore.file.download.DownloadHelper;
import com.jianghu.mscore.file.download.ExcelDownload;
import com.jianghu.mscore.file.download.ImageDownload;
import com.jianghu.mscore.file.exception.FileException;
import com.jianghu.mscore.file.upload.AbstractUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件管理类
 * 注入{@link com.jianghu.mscore.file.upload.AbstractUpload}所有实现类
 * {@link com.jianghu.mscore.file.download.ExcelDownload}
 * 及{@link com.jianghu.mscore.file.download.ImageDownload}
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
@Component
public class FileManager {

    @Resource
    private List<AbstractUpload> uploadList;

    /**
     * 检查文件类型合法性，并进行上传处理
     *
     * @param file            the file
     * @param resultOrProcess the result or process
     * @throws Exception the exception
     * @since 2019.06.11
     */
    public void upload(MultipartFile file, Object resultOrProcess) throws Exception {
        for (AbstractUpload upload : uploadList) {
            if (upload.checkFileType(file)) {
                upload.upload(file, resultOrProcess);
                return;
            }
        }
        String suffix;
        throw new FileException("文件类型不合法" + (StringUtils.isEmpty(suffix = FileTypeUtil.getFileSuffix(file)) ? ":" : ":" + suffix));
    }

    @Resource
    private ExcelDownload excelDownload;

    /**
     * excel分页下载
     *
     * @param response the response
     * @param template the template
     * @throws IOException the io exception
     * @since 2019.06.11
     */
    public void excelDownload(HttpServletResponse response, ExcelTemplate template) throws IOException {
        excelDownload.download(response, (res, stream) -> {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            List<List<String>> dataRow = template.getDataList();
            int realCount = (int) (Math.ceil(dataRow.size() / 10000.00));
            for (int j = 0; j <= (realCount == 0 ? 0 : realCount - 1); j++) {
                template.exportExcel(j,
                        dataRow.subList(j * 10000, (j + 1) * 10000 > dataRow.size() ? dataRow.size() : (j + 1) * 10000),
                        hssfWorkbook);
            }
            //原理就是将所有的数据一起写入，然后再关闭输入流。
            hssfWorkbook.write(stream);
            stream.close();
        });
    }

    @Resource
    private ImageDownload imageDownload;

    /**
     * 图片下载
     *
     * @param response the response
     * @param helper   the helper
     * @throws IOException the io exception
     * @since 2019.06.11
     */
    public void imageDownload(HttpServletResponse response, DownloadHelper helper) throws IOException {
        imageDownload.download(response, helper);
    }



}
