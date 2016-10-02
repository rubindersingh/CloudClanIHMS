package com.asu.cloudclan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class GreetingController {

    @RequestMapping("/greetme")
    public String index() {
        return "greetings";
    }
}