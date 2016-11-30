package com.asu.cloudclan.controller;

import com.asu.cloudclan.service.RegistrationService;
import com.asu.cloudclan.service.UploadService;
import com.asu.cloudclan.vo.RegistrationVO;
import com.asu.cloudclan.vo.UploadVO;
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
public class RegistrationController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${instanceId}")
    private int instanceId;
    @Autowired
    RegistrationService registrationService;

    @RequestMapping(value="/registration", method = RequestMethod.POST, produces = {"application/json"})
    public RegistrationVO register(RegistrationVO registrationVO) {
        log.info("Web "+instanceId+": Request for registration");
        registrationService.register(registrationVO);
        return registrationVO;
    }

}

