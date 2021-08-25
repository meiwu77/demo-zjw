package com.yuantu.demo.web.config.strDateConfig;//package com.yuantu.medical.skill.web.config.strDateConfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.support.GenericConversionService;
//import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
//
//import javax.annotation.PostConstruct;
//
///**
// * String日期格式转换Date，用于接收前端传入的String 日期格式
// */
//@Configuration
//public class StrDateConvertConfig {
//
//    @Autowired
//    private RequestMappingHandlerAdapter handlerAdapter;
//
//    /**
//     * 增加字符串转日期的功能
//     */
//    @PostConstruct
//    public void initEditableAvlidation() {
//        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
//        if(initializer.getConversionService()!=null) {
//            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
//            //转换类
//            genericConversionService.addConverter(new CustomDateConverter());
//        }
//    }
//
//
//}
