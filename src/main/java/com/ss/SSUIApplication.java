package com.ss;

import com.asu.cloudclan.entity.cassandra.ImageMetadata;
import com.asu.cloudclan.service.cassandra.ImageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ss")
@EnableAutoConfiguration
public class SSUIApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SSUIApplication.class, args);
	}

}
