package com.asu.cloudclan.controller;

import com.asu.cloudclan.service.cassandra.ContainerCoreService;
import com.asu.cloudclan.vo.ContainerVO;
import com.asu.cloudclan.vo.HomeVO;
import com.asu.cloudclan.vo.UsageDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ContainerCoreService containerCoreService;

    @RequestMapping(value = {"/", "/index*"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/register*", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/home*", method = RequestMethod.GET)
    public String home(ModelMap model) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        HomeVO homeVO = containerCoreService.getUsageAndContainers(currentUserEmail);
        UsageDataVO usageDataVO = homeVO.getUsageDataVO();
        model.addAttribute("usageDataVO", usageDataVO);
        List<ContainerVO> containerVOs = homeVO.getContainerVOs();
        model.addAttribute("containerVOs", containerVOs);
        return "home";
    }
}