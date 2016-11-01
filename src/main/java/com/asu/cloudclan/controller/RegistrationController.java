package com.asu.cloudclan.controller;

import com.asu.cloudclan.service.RegistrationService;
import com.asu.cloudclan.service.UploadService;
import com.asu.cloudclan.vo.RegistrationVO;
import com.asu.cloudclan.vo.UploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rubinder on 10/25/16.
 */
@RestController
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @RequestMapping(value="/registration", method = RequestMethod.POST, produces = {"application/json"})
    public RegistrationVO register(RegistrationVO registrationVO) {
        registrationService.register(registrationVO);
        return registrationVO;
    }

}

