package com.yuantu.demo.web.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.yuantu.common.date.DateUtil;
import com.yuantu.common.result.Result;
import com.yuantu.plancenter.domain.request.RequestPageSchInfoParams;
import com.yuantu.plancenter.manager.PlancenterManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zhangJingWei
 * @date 2021/7/5 17:49
 * @desc
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping("/testSentinel")
    @SentinelResource("testSentinel")
    public Result testSentinel(){

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.createSuccessResult();
    }

}
