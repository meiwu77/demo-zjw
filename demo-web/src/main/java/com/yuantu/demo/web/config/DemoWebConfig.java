package com.yuantu.demo.web.config;

import com.yuantu.common.sms.SMSUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZhangJingWei
 */
@Configuration
public class DemoWebConfig implements WebMvcConfigurer {

    Logger logger = LoggerFactory.getLogger(DemoWebConfig.class);


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//
//        //字段非空校验
//        registry.addInterceptor(new RequestParamsNotEmptyInterceptor())
//                .addPathPatterns("/**");
//        //网关签名检验
//        registry.addInterceptor(new FrontGatewaySignInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("static/**").addResourceLocations("classpath:/static/");
    }

//    @Bean
//    public YtUserHandlerMethodArgumentResolver ytUserHandlerMethodArgumentResolver() {
//        return new YtUserHandlerMethodArgumentResolver();
//    }

//    /**
//     * 自定义参数解析器
//     */
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(ytUserHandlerMethodArgumentResolver());
//    }


    /**
     * cors 跨域配置
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        CorsConfiguration config = new CorsConfiguration();
        //允许cookie跨域
        config.setAllowCredentials(true);
        config.setAllowedMethods(Arrays.asList("POST", "GET", "OPTIONS", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Sea-Mock", "Origin", "X-Requested-With", "Content-Type", "Accept", "access_token"));
        config.setExposedHeaders(Arrays.asList("Sea-Mock", "Origin", "X-Requested-With", "Content-Type", "Accept", "access_token"));
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        CorsFilter filter = new CorsFilter(configSource);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
//
//    @Bean
//    public FilterRegistrationBean seaGlobalFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new SeaGlobalFilter());
//        registration.addUrlPatterns("/*");
////        registration.addInitParameter("filter", "/api/,/restapi/"); //添加默认参数
////        registration.addInitParameter("noFilter", "");
//        registration.setName("SeaGlobalFilter");
////        registration.setOrder(1);//设置优先级
//        return registration;
//    }

    /**
     * 404 500 错误页面
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer() {

        return (container -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

            container.addErrorPages(error404Page, error500Page);
        });
    }

    /**
     * 解决中文乱码
     */
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    /**
     * 短信发送
     */
    @Bean
    public SMSUtilService smsUtilService() {
        return new SMSUtilService();
    }


}
