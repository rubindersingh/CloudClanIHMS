package com.asu.cloudclan.controller;

import com.asu.cloudclan.entity.cassandra.SignupUser;
import com.asu.cloudclan.entity.cassandra.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /*@RequestMapping(value="/login1", method = RequestMethod.POST)
    public String login(){
        return "Data saved in db";
    }*/

    @GetMapping("/login1")
    public String signUpForm(Model model) {
        model.addAttribute("signupUser", new SignupUser());
        return "Sign up User";
    }

    @PostMapping("/login1")
    public String signUpSubmit(@ModelAttribute SignupUser signupUser) {
        return "User signed up";
    }

}