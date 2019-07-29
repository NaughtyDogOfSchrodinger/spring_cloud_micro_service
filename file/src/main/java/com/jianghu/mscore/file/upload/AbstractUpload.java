package com.jianghu.mscore.file.upload;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.jianghu.mscore.file.constant.FileSuffix;
import com.jianghu.mscore.file.constant.FileType;
import com.jianghu.mscore.file.util.FileTypeUtil;
import com.jianghu.mscore.file.constant.OssProperties;
import com.jianghu.mscore.file.exception.FileException;
import com.jianghu.mscore.file.vo.FileResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * 对上传接口{@link Upload}做基本实现
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.11
 */
public abstract class AbstractUpload implements Upload {

    /**
     * The Mb.
     */
    protected long mb = 1024 * 1024L;

    @Resource
    private OssProperties ossProperties;

    private OSS oss;

    /**
     * 检查文件大小
     *
     * @param file the file
     * @return the boolean
     * @since 2019.06.11
     */
    protected abstract boolean checkFileSize(MultipartFile file);

    /**
     * 获取文件大小上限
     *
     * @param file the file
     * @return the max file size
     */
    protected abstract long getMaxFileSize(MultipartFile file);

    /**
     * 检查文件类型
     *
     * @param suffix the suffix
     * @param file   the file
     * @return the boolean
     * @throws IOException the io exception
     * @since 2019.06.11
     */
    boolean checkFileType(FileSuffix suffix, MultipartFile file) throws IOException {
        FileType type = FileTypeUtil.getType(file.getInputStream());
        if (type == null) return false;
        String fileSuffix;
        if (StringUtils.isEmpty(fileSuffix = FileTypeUtil.getFileSuffix(file))) return false;
        FileType[] types = suffix.getTypes();
        for (FileType fileType : types) {
            if (type == fileType ||
                    fileType.name().contains("_") ? (fileType.name().toLowerCase().startsWith(fileSuffix.toLowerCase())
                    || fileType.name().toLowerCase().endsWith(fileSuffix.toLowerCase()))
            : Objects.equals(fileSuffix.toLowerCase(), fileType.name().toLowerCase())) return true;
        }
        return false;
    }

    /**
     * 上传或处理文件
     *
     * @param file   the file
     * @param result the result
     * @throws Exception the exception
     * @since 2019.06.11
     */
    public void upload(MultipartFile file, Object result) throws Exception{
        if (result instanceof FileResult) {
            upload(file, (FileResult) result);
        } else if (result instanceof AbandonedUpload) {
            if (checkFileSize(file)) throw new FileException("文件大小不得超过: " + getMaxFileSize(file) + "M");
            ((AbandonedUpload) result).upload(file);
        }
    }

    public void upload(MultipartFile file, FileResult result) {
        if (checkFileSize(file)) throw new FileException("文件大小不得超过: " + getMaxFileSize(file) + "M");
        try {
            oss = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
            if (!oss.doesBucketExist(ossProperties.getBucketName())) {
                /*
                 * Create a new OSS bucket
                 */
                System.out.println("Creating bucket " + ossProperties.getBucketName() + "\n");
                oss.createBucket(ossProperties.getBucketName());
                CreateBucketRequest createBucketRequest= new CreateBucketRequest(ossProperties.getBucketName());
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                oss.createBucket(createBucketRequest);
            }
            String key = result.getRand() + FileTypeUtil.SEPARATOR + new Random().nextInt(10000) + System.currentTimeMillis() + FileTypeUtil.POINT + FileTypeUtil.getFileSuffix(file);
            oss.putObject(new PutObjectRequest(ossProperties.getBucketName(), key, file.getInputStream()));
            int index = ossProperties.getEndpoint().lastIndexOf(FileTypeUtil.SEPARATOR);
            String url = ossProperties.getEndpoint().substring(0, index) + ossProperties.getBucketName() + FileTypeUtil.POINT + ossProperties.getEndpoint().substring(index + 1) + FileTypeUtil.SEPARATOR + key;
            result.setKey(key);
            result.setUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oss.shutdown();
        }
    }


}
