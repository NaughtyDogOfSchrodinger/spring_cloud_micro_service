package com.jianghu.mscore.file.upload;

import com.jianghu.mscore.file.constant.FileSuffix;
import com.jianghu.mscore.file.upload.AbstractUpload;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class PdfUpload extends AbstractUpload {

    private long maxSize = 10L;

    @Override
    public boolean checkFileType(MultipartFile file) throws IOException {
        return checkFileType(FileSuffix.PDF, file);
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
