package com.yuantu.demo.web.exception;

import com.alibaba.dubbo.rpc.RpcException;
import com.github.spy.sea.sofa.tracer.plugins.simple.util.TraceUtil;
import com.yuantu.common.result.Result;
import com.yuantu.common.result.ResultCodeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


/**
 * 全局异常处理
 *
 * @author zhangjingwei
 */
@ControllerAdvice
public class GlobalExceptionHandle {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result Handle(Exception e) {
        Result result = null;

        if (e instanceof RpcException) {
            log.error("[系统异常]", e);
            result = Result.error("服务异常!" + e.getMessage());
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            log.error("[参数异常]", e);
            result = Result.error(ResultCodeEnums.PARAM_VALIDATE_ERROR);
        } else if (e instanceof BindException) {
            //请求参数绑定到controller请求参数时的异常(controller方法没有BindingResult参数)
            log.error("[参数绑定异常]", e);
            BindException exception = (BindException) e;
            BindingResult bindingResult = exception.getBindingResult();
            FieldError fieldError = bindingResult.getFieldError();
            result = Result.error(ResultCodeEnums.PARAM_VALIDATE_ERROR,
                    "[" + fieldError.getField() + "]" + fieldError.getDefaultMessage());

        } else if (e instanceof ConstraintViolationException) {
            //javax.validation:validation-api 校验参数抛出的异常
            log.error("[参数校验异常]", e);
            ConstraintViolationException exception = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
            ConstraintViolation<?> violation = violations.iterator().next();
            String message = violation.getMessage();
            result = Result.error(message);
        } else {
            log.error("[系统异常]", e);
            result = Result.error("系统异常，请稍后重试！" + e.getMessage());
        }

        if (result != null) {
            result.setTraceId(TraceUtil.getTraceId());
            result.setSpanId(TraceUtil.getSpanId());
        }

        return result;
    }
}

