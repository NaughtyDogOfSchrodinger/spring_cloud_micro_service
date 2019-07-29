package com.jianghu.mscore.file.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OssProperties {

    @Value("${oss.endpoint:https://oss-cn-qingdao.aliyuncs.com}")
    private String endpoint;

    @Value("${oss.accessKeyId:rnuZHLAdmKbIBAlq}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret:UtZZwW5EOgxwZQFT0psfnlxzm3LeY4}")
    private String accessKeySecret;

    @Value("${oss.bucketName:customer-service-bucket}")
    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }
}
