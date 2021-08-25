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
@DisconfFile(filename = "sms_template.properties")
public class SmsTemplate {

    /** 预约成功提醒 */
    private static String appointSuc;

    /** 检查前提醒 */
    private static String examRemind;

    /** 停止排班（包含设备停用） */
    private static String schResultClose;

    /** 取消预约提醒 */
    private static String cancelSchOrder;

    /** 提交审核 */
    private static String submitVerify;

    /** 审核通过 */
    private static String verifySuc;

    /** 审核不通过 */
    private static String verifyFail;

    @DisconfFileItem(name = "appointSuc", associateField = "appointSuc")
    public static String getAppointSuc() {
        return appointSuc;
    }
    @DisconfFileItem(name = "examRemind", associateField = "examRemind")
    public static String getExamRemind() {
        return examRemind;
    }
    @DisconfFileItem(name = "schResultClose", associateField = "schResultClose")
    public static String getSchResultClose() {
        return schResultClose;
    }
    @DisconfFileItem(name = "cancelSchOrder", associateField = "cancelSchOrder")
    public static String getCancelSchOrder() {
        return cancelSchOrder;
    }


    @DisconfFileItem(name = "submitVerify", associateField = "submitVerify")
    public static String getSubmitVerify() {
        return submitVerify;
    }
    @DisconfFileItem(name = "verifySuc", associateField = "verifySuc")
    public static String getVerifySuc() {
        return verifySuc;
    }
    @DisconfFileItem(name = "verifyFail", associateField = "verifyFail")
    public static String getVerifyFail() {
        return verifyFail;
    }

}
