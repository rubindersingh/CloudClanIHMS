package com.asu.cloudclan.controller;

import com.asu.cloudclan.entity.cassandra.User;
import com.asu.cloudclan.vo.ContainerVO;
import com.asu.cloudclan.vo.UsageDataVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public String home(ModelMap model) {
        UsageDataVO usageDataVO = new UsageDataVO(10, 100, 20, "100 MB", "1000 MB", "2000 MB");
        model.addAttribute("usageDataVO", usageDataVO);
        List<ContainerVO> containerVOs = new ArrayList<>();
        ContainerVO containerVO = new ContainerVO("gcbfbff", "PUBLIC", "My COntainer 1");
        ContainerVO containerVO1 = new ContainerVO("gcbfbff", "PRIVATE", "My COntainer 2");
        ContainerVO containerVO2 = new ContainerVO("gcbfbfcmhbhf", "PUBLIC", "My COntainer 3");
        containerVOs.add(containerVO); containerVOs.add(containerVO1); containerVOs.add(containerVO2);
        model.addAttribute("containerVOs", containerVOs);
        return "home";
    }
}