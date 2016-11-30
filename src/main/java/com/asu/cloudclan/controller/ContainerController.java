package com.asu.cloudclan.controller;

import com.asu.cloudclan.service.ContainerService;
import com.asu.cloudclan.service.RegistrationService;
import com.asu.cloudclan.vo.ContainerVO;
import com.asu.cloudclan.vo.RegistrationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rubinder on 10/25/16.
 */
@RestController
public class ContainerController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${instanceId}")
    private int instanceId;
    @Autowired
    ContainerService containerService;

    @RequestMapping(value="/containers", method = RequestMethod.POST, produces = {"application/json"})
    public ContainerVO create(ContainerVO containerVO) {
        log.info("Web "+instanceId+": Request for create container");
        containerService.create(containerVO);
        return containerVO;
    }

    @RequestMapping(value="/containers/share", method = RequestMethod.POST, produces = {"application/json"})
    public ContainerVO share(ContainerVO containerVO) {
        log.info("Web "+instanceId+": Request for share container");
        containerService.share(containerVO);
        return containerVO;
    }
}

