package com.yuantu.demo.web.config.sms;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Splitter;
import com.yuantu.common.result.Result;
import com.yuantu.common.sms.SMSUtilService;
import com.yuantu.common.sms.common.dto.SMSParamsDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

import static com.alibaba.fastjson.JSON.parseObject;

@Component
public class SMSUtil {

    private final static Logger logger = LoggerFactory.getLogger(SMSUtil.class);

    public static final String GB2312 = "GB2312";
    public static final String UTF8 = "UTF-8";

    @Resource
    private SMSUtilService smsUtilService;
    @Resource
    private SmsConfig smsConfig;

    private static SMSUtil smsUtil;


    @PostConstruct
    public void init() {
        smsUtil = this;
        smsUtil.smsUtilService = this.smsUtilService;
        smsUtil.smsConfig = this.smsConfig;
    }

    /**
     * 发送短信,多个号码用逗号隔开
     *
     * @param phones
     * @param msg
     * @return void
     * @author yekaiqiang
     * @date 2016/12/13 16:27
     */
    public static void sendMsg(String phones, String msg, Long unionId) {
        try {

            SMSParamsDTO smsParamsDTO = convertSMSParamsDTO(phones, msg, unionId);

            logger.info("开始调用短信接口:phone is " + phones + ",sendAddress is " + smsParamsDTO.getSendAddress() + ",msg is " + msg);
            smsUtil.smsUtilService.sendMsgV2(smsParamsDTO);
            logger.info("调用短信接口完成：手机号-{},result信息-{}", phones, msg);
        } catch (Exception e) {
            logger.error("phone=" + phones, e);
        }
    }

    private static SMSParamsDTO convertSMSParamsDTO(String phone, String msg, Long unId) {
        String unionId = "-1";
        if (null != unId) {
            unionId = String.valueOf(unId);
        }


        String smsKey = null;
        //根据医联体id  获取该医联体的短信通道类型
        String unionListStr = smsUtil.smsConfig.getUnionIdList();
        Map<String, String> mapUnions = parseObject(unionListStr, new TypeReference<Map<String, String>>() {
        });
        if (mapUnions != null) {
            for (String key : mapUnions.keySet()) {
                String str = mapUnions.get(key);
                Iterable<String> iter = Splitter.on(",").omitEmptyStrings().split(str);
                for (String uid : iter) {
                    if (uid.equals(unionId)) {
                        smsKey = key;
                        break;
                    }
                }
            }
        }


        //根据key值   获取短信通道
        String sms = smsUtil.smsConfig.getSms();
        Map<String, SmsVO> mapSms = parseObject(sms, new TypeReference<Map<String, SmsVO>>() {
        });
        SmsVO smsVO = mapSms.get(smsKey);

        if (smsVO == null) {

            smsVO = mapSms.get("defaultSms");
        }

        SMSParamsDTO smsParamsDTO = new SMSParamsDTO();
        smsParamsDTO.setPhones(phone);
        smsParamsDTO.setMsg(msg);
        smsParamsDTO.setProvider(smsVO.getProvider());
        smsParamsDTO.setAccount(smsVO.getAccount());
        smsParamsDTO.setExtno(smsVO.getExtno());
        smsParamsDTO.setNeedstatus(smsVO.getNeedstatus());
        smsParamsDTO.setProduct(smsVO.getProduct());
        smsParamsDTO.setPswd(smsVO.getPswd());
        smsParamsDTO.setSendAddress(smsVO.getSendAddress());
        smsParamsDTO.setSmsParam(smsVO.getSmsParam());
        smsParamsDTO.setClientId(UUID.randomUUID().toString());


        return smsParamsDTO;
    }

    /**
     * 发送短信,多个号码用逗号隔开
     * （此方法支持在发送短信时传递透传参数，短信网关的返回值放在Result的msg中。虽然看起来有点奇怪，但是做停诊需要返回值，
     * 并且发送短信被很多地方调用，所以这里还需要做兼容，故放到msg中）
     *
     * @param phones
     * @param msg
     * @param unionId
     * @param passthroughValue 透传参数
     */
    public static Result<SMSParamsDTO> sendMsg2(String phones, String msg, Long unionId, String passthroughValue) {
        Result<SMSParamsDTO> result = Result.createSuccessResult();
        try {

            SMSParamsDTO smsParamsDTO = convertSMSParamsDTOV2(phones, msg, unionId, passthroughValue);

            logger.info("开始调用短信接口:phone is " + phones + ",sendAddress is " + smsParamsDTO.getSendAddress() + ",msg is " + msg);
            Result<Boolean> booleanResult = smsUtil.smsUtilService.sendMsgV2(smsParamsDTO);
            result.setMsg(booleanResult.getMsg());
            result.setData(smsParamsDTO);
            if (!booleanResult.isSuccess()) {
                result = Result.createFailResult();
                result.setMsg(booleanResult.getMsg());
            }
            logger.info("调用短信接口完成：手机号-{},result信息-{}", phones, msg);
        } catch (Exception e) {
            logger.error("phone=" + phones, e);
        }
        return result;
    }

    private static SMSParamsDTO convertSMSParamsDTOV2(String phone, String msg, Long unId, String passthroughValue) {
        String unionId = "-1";
        if (null != unId) {
            unionId = String.valueOf(unId);
        }

        String smsKey = null;
        //根据医联体id  获取该医联体的短信通道类型
        String unionListStr = smsUtil.smsConfig.getUnionIdList();
        Map<String, String> mapUnions = parseObject(unionListStr, new TypeReference<Map<String, String>>() {
        });
        if (mapUnions != null) {
            for (String key : mapUnions.keySet()) {
                String str = mapUnions.get(key);
                Iterable<String> iter = Splitter.on(",").omitEmptyStrings().split(str);
                for (String uid : iter) {
                    if (uid.equals(unionId)) {
                        smsKey = key;
                        break;
                    }
                }
            }
        }


        //根据key值   获取短信通道
        String sms = smsUtil.smsConfig.getSms();
        Map<String, SmsVO> mapSms = parseObject(sms, new TypeReference<Map<String, SmsVO>>() {
        });
        SmsVO smsVO = mapSms.get(smsKey);

        if (smsVO == null) {
            smsVO = mapSms.get("defaultSms");
        }

        SMSParamsDTO smsParamsDTO = new SMSParamsDTO();
        smsParamsDTO.setPhones(phone);
        smsParamsDTO.setMsg(msg);
        smsParamsDTO.setProvider(smsVO.getProvider());
        smsParamsDTO.setAccount(smsVO.getAccount());
        smsParamsDTO.setExtno(smsVO.getExtno());
        smsParamsDTO.setNeedstatus(smsVO.getNeedstatus());
        smsParamsDTO.setProduct(smsVO.getProduct());
        smsParamsDTO.setPswd(smsVO.getPswd());
        smsParamsDTO.setSendAddress(smsVO.getSendAddress());
        smsParamsDTO.setSmsParam(smsVO.getSmsParam());
        smsParamsDTO.setClientId(UUID.randomUUID().toString());


        // 创蓝和集时通是可以传透传参数的，索游不支持传透传参数
        if (StringUtils.isNotBlank(smsVO.getPassthroughParam())) {
            smsParamsDTO.setPassthroughParam(smsVO.getPassthroughParam());
            smsParamsDTO.setPassthroughValue(passthroughValue);
        }

        return smsParamsDTO;
    }
}
