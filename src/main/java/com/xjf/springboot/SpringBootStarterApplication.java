package com.xjf.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.xjf.controller")
@MapperScan("com.xjf.mapper")
public class SpringBootStarterApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootStarterApplication.class, args);
	}
}
