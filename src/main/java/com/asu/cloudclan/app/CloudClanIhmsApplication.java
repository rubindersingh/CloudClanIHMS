package com.asu.cloudclan.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.asu.cloudclan")
@EnableAutoConfiguration
public class CloudClanIhmsApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CloudClanIhmsApplication.class, args);
	}

}
