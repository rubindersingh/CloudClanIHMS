package com.asu.cloudclan.app;

import com.asu.cloudclan.entity.cassandra.Image;
import com.asu.cloudclan.entity.cassandra.ImageMetadata;
import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ContainerType;
import com.asu.cloudclan.service.cassandra.ContainerService;
import com.asu.cloudclan.service.cassandra.ImageService;
import com.asu.cloudclan.service.rabbitmq.RabbitMQSenderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan(basePackages = "com.asu.cloudclan")
@EnableAutoConfiguration
public class CloudClanIhmsApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CloudClanIhmsApplication.class, args);
		ImageService imageService = (ImageService) ctx.getBean("imageService");
		//Image image = new Image("/my/image/2.jpg","mycont1",100,0,new HashMap<>());
		ImageMetadata metadata = new ImageMetadata("jhjhhbhmvv",50,50,1000,"w=50&h=50","image/jpeg");
		//imageService.addTransformation("/my/image/2.jpg",metadata);
		imageService.doTransformation();
		System.out.println("Hello");
	}

}
