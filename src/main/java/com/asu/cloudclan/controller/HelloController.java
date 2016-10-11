package com.asu.cloudclan.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/hello")
	public String hello(@RequestParam String name) {
		return "Hello "+name;
	}
	
    @RequestMapping("/")
    @Cacheable("calculateResult")
    public String index() {
        System.out.println("Performing expensive calculation...");
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/login")
    public String login(@RequestParam String uname) {
        return "Hello "+uname;
    }
}