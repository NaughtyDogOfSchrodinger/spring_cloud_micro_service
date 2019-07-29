package com.jianghu.mscore.file.upload;

import com.jianghu.mscore.exception.BaseException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 不做上传的文件
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
@FunctionalInterface
public interface AbandonedUpload {

    /**
     * 处理该文件
     *
     * @param abandonedFile the abandoned file
     * @throws BaseException the base exception
     * @since 2019.06.11
     */
    void upload(MultipartFile abandonedFile) throws BaseException;
}
