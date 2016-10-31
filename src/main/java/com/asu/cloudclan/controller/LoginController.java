package com.asu.cloudclan.controller;

import com.asu.cloudclan.entity.cassandra.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @RequestMapping(value = "/signin*", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register*", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/home*", method = RequestMethod.GET)
    public String home() {
        return "home";
    }
}