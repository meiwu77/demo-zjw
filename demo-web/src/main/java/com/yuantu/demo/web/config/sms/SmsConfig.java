package com.yuantu.demo.web.config.sms;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author zhangjingwei
 */
@Component
@Scope("singleton")
@DisconfFile(filename = "sms_config.properties")
public class SmsConfig {

    private String sms;
    private String unionIdList;

    @DisconfFileItem(name = "config.sms", associateField = "sms")
    public String getSms() {
        return  sms;
    }
    @DisconfFileItem(name = "config.unionIdList", associateField = "unionIdList")
    public String getUnionIdList() {
        return  unionIdList;
    }
}
