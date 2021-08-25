package com.yuantu.demo.web.config.sms;

import lombok.Data;

@Data
public class SmsVO {
    private String provider;
    private String account;
    private String pswd;
    private String product;
    private String sendAddress;

    private String smsParam;
    private String needstatus;
    private String extno;

    // 透传参数
    private String passthroughParam;
}
