package com.jianghu.mscore.file.upload;

import com.jianghu.mscore.file.Loadable;
import com.jianghu.mscore.file.vo.FileResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传接口
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
public interface Upload extends Loadable {

    /**
     * 上传文件，封装结果值
     *
     * @param file   the file
     * @param result the result
     * @since 2019.06.11
     */
    void upload(MultipartFile file, FileResult result);

    /**
     * 检查文件类型是否合法
     *
     * @param file the file
     * @return the boolean
     * @throws IOException the io exception
     * @since 2019.06.11
     */
    boolean checkFileType(MultipartFile file) throws IOException;

}
