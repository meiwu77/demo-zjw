package com.yuantu.demo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ZhangJingWei
 * springboot 启动类
 */
//@ImportResource(value={"classpath*:dubbo-*.xml"})
@EnableTransactionManagement
@SpringBootApplication(
		scanBasePackages = {"com.yuantu.demo.web","com.yuantu.plancenter.config"}
)
public class DemoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebApplication.class, args);
	}

}
