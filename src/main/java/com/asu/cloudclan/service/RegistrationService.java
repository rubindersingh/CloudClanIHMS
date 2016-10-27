package com.asu.cloudclan.service;

import com.asu.cloudclan.entity.cassandra.User;
import com.asu.cloudclan.service.cassandra.UserService;
import com.asu.cloudclan.util.ValidationUtil;
import com.asu.cloudclan.vo.ErrorVO;
import com.asu.cloudclan.vo.RegistrationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rubinder on 10/25/16.
 */
@Service
public class RegistrationService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegistrationVO registrationVO) {
        if(registrationVO!=null) {
            List<ErrorVO> errorVOs = new ArrayList<>();

            if(registrationVO.getPassword() == null) {
                errorVOs.add(new ErrorVO("password", messageSource.getMessage("password.null", null, Locale.getDefault())));
            }

            if(registrationVO.getFirstName() == null) {
                errorVOs.add(new ErrorVO("firstName", messageSource.getMessage("first.name.null", null, Locale.getDefault())));
            }

            if(registrationVO.getLastName() == null) {
                errorVOs.add(new ErrorVO("lastName", messageSource.getMessage("last.name.null", null, Locale.getDefault())));
            }

            if(registrationVO.getEmailId() == null) {
                errorVOs.add(new ErrorVO("emailId", messageSource.getMessage("email.id.null", null, Locale.getDefault())));
            } else {
                if(!ValidationUtil.isvalidEmail(registrationVO.getEmailId())) {
                    errorVOs.add(new ErrorVO("emailId", messageSource.getMessage("email.id.invalid", null, Locale.getDefault())));
                }
            }

            if(errorVOs.isEmpty()) {
                User user = userService.find(registrationVO.getEmailId());
                if(user == null) {
                    user = new User(registrationVO.getEmailId(), passwordEncoder.encode(registrationVO.getPassword()), registrationVO.getFirstName(), registrationVO.getLastName());
                    userService.create(user);
                }
            } else {
                registrationVO.setErrorVOs(errorVOs);
            }
        }
    }
}
