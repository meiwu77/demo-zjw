package com.yuantu.demo.web.config.oss;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * oss 配置
 * @author zhangjingwei
 */
@Component
@ConfigurationProperties(prefix = "config.oss")
@Validated
public class OssEnv {
    /** 排班系统 oss host */
    @NotBlank
    private String paibanOssHost;

    private String endpoint;

    private String accessKeyId ;

    private String accessKeySecret ;

    private String bucketName;



    public String getPaibanOssHost() {
        return paibanOssHost;
    }

    public void setPaibanOssHost(String paibanOssHost) {
        this.paibanOssHost = paibanOssHost;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
