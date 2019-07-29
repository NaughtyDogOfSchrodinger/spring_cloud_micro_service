package com.jianghu.mscore.file.upload;

import com.jianghu.mscore.file.constant.FileSuffix;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class RarUpload extends AbstractUpload {

    private long maxSize = 30L;

    @Override
    public boolean checkFileType(MultipartFile file) throws IOException {
        return checkFileType(FileSuffix.RAR, file);
    }

    @Override
    protected boolean checkFileSize(MultipartFile file) {
        return file.getSize() > maxSize * mb;
    }

    @Override
    protected long getMaxFileSize(MultipartFile file) {
        return maxSize;
    }

}
