package com.asu.cloudclan.controller;

import com.asu.cloudclan.service.ContainerService;
import com.asu.cloudclan.service.RegistrationService;
import com.asu.cloudclan.vo.ContainerVO;
import com.asu.cloudclan.vo.RegistrationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rubinder on 10/25/16.
 */
@RestController
public class ContainerController {

    @Autowired
    ContainerService containerService;

    @RequestMapping(value="/containers", method = RequestMethod.POST, produces = {"application/json"})
    public ContainerVO create(ContainerVO containerVO) {
        containerService.create(containerVO);
        return containerVO;
    }

    @RequestMapping(value="/containers/share", method = RequestMethod.POST, produces = {"application/json"})
    public ContainerVO share(ContainerVO containerVO) {
        containerService.share(containerVO);
        return containerVO;
    }
}

