package com.yuantu.demo.web.config.redisConfig;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Component
@ConfigurationProperties(prefix = "spring.redis")
@Validated
@Data
public class RedisEnv {

    /** 排班使用的 database*/
    @NotNull
    private Integer planDatabase;
    @NotNull
    private Integer database;


    @NotBlank
    private String planHost;
    @NotBlank
    private String host;


    @NotBlank
    private String planPassword;
    @NotBlank
    private String password;


    @NotNull
    private Integer planPort;
    @NotNull
    private Integer port;






}
