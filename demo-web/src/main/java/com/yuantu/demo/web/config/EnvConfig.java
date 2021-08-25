package com.yuantu.demo.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * env 环境变量
 * @author ZhangJingWei
 */
@Component
@ConfigurationProperties(prefix = "config.env")
public class EnvConfig {
    public static String envName;
    public static Boolean platform;

    public static String getEnvName() {
        return envName;
    }

    public  void setEnvName(String envName) {
        EnvConfig.envName = envName;
    }

    public  Boolean getPlatform() {
        return platform;
    }

    public void setPlatform(Boolean platform) {
        EnvConfig.platform = platform;
    }
}
