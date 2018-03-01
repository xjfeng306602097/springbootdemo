package com.xjf.springboot;

import com.xjf.config.SpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.xjf")
@Import(SpringConfiguration.class)
public class SpringBootStarterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootStarterApplication.class, args);
	}
}
