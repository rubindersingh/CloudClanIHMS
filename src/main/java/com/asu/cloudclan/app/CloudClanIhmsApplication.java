package com.asu.cloudclan.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.asu.cloudclan")
@EnableAutoConfiguration
public class CloudClanIhmsApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CloudClanIhmsApplication.class, args);
	}
}
