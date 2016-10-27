package com.asu.cloudclan.service;

import com.asu.cloudclan.entity.cassandra.User;
import com.asu.cloudclan.entity.cassandra.UserContainer;
import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ContainerType;
import com.asu.cloudclan.service.cassandra.ContainerCoreService;
import com.asu.cloudclan.service.cassandra.UserService;
import com.asu.cloudclan.vo.ContainerVO;
import com.asu.cloudclan.vo.ErrorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rubinder on 10/25/16.
 */
@Service
public class ContainerService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired()
    private ContainerCoreService containerCoreService;
    @Autowired()
    private UserService userService;

    public void create(ContainerVO containerVO) {

        if(containerVO!=null) {
            try {
                List<ErrorVO> errorVOs = new ArrayList<>();
                if(containerVO.getName() == null) {
                    errorVOs.add(new ErrorVO("name", messageSource.getMessage("container.name.null", null, Locale.getDefault())));
                }

                if(containerVO.getType() == null) {
                    errorVOs.add(new ErrorVO("type", messageSource.getMessage("container.type.null", null, Locale.getDefault())));
                } else {
                    ContainerType containerType = ContainerType.valueOf(containerVO.getType());
                    if(containerType == null) {
                        errorVOs.add(new ErrorVO("type", messageSource.getMessage("container.type.invalid", null, Locale.getDefault())));
                    }
                }

                if(errorVOs.isEmpty()) {
                    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
                    containerCoreService.createAndMap(currentUserEmail, containerVO);
                } else {
                    containerVO.setErrorVOs(errorVOs);
                }
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                ErrorVO errorVO = new ErrorVO(messageSource.getMessage("server.error",null,null));
                List<ErrorVO> errorVOs = new ArrayList<>(1);
                errorVOs.add(errorVO);
                containerVO.setErrorVOs(errorVOs);
            }
        }
    }

    public void share(ContainerVO containerVO) {
        if(containerVO!=null) {
            try {
                List<ErrorVO> errorVOs = new ArrayList<>();
                if(containerVO.getType() == null) {
                    errorVOs.add(new ErrorVO("type", messageSource.getMessage("access.type.null", null, Locale.getDefault())));
                } else {
                    AccessType accessType = AccessType.valueOf(containerVO.getType());
                    if(accessType == null || accessType == AccessType.A) {
                        errorVOs.add(new ErrorVO("type", messageSource.getMessage("access.type.invalid", null, Locale.getDefault())));
                    }
                }

                if(containerVO.getId() == null) {
                    errorVOs.add(new ErrorVO("id", messageSource.getMessage("container.id.null", null, Locale.getDefault())));
                }

                if(containerVO.getEmailId() == null) {
                    errorVOs.add(new ErrorVO("emailId", messageSource.getMessage("email.id.null", null, Locale.getDefault())));
                }

                if(errorVOs.isEmpty()) {
                    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
                    UserContainer userContainer = containerCoreService.find(currentUserEmail, containerVO.getId());
                    if(userContainer == null || !(AccessType.valueOf(userContainer.getAccessType()) == AccessType.A
                            || AccessType.valueOf(userContainer.getAccessType()) == AccessType.AD)) {
                        errorVOs.add(new ErrorVO(messageSource.getMessage("insufficient.privileges", null, Locale.getDefault())));
                    } else {
                        User user = userService.find(containerVO.getEmailId());
                        if(user == null) {
                            errorVOs.add(new ErrorVO("emailId", messageSource.getMessage("email.id.invalid", null, Locale.getDefault())));
                        } else {
                            containerCoreService.map(containerVO);
                        }
                    }
                    if(!errorVOs.isEmpty()) {
                        containerVO.setErrorVOs(errorVOs);
                    }
                } else {
                    containerVO.setErrorVOs(errorVOs);
                }
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                ErrorVO errorVO = new ErrorVO(messageSource.getMessage("server.error",null,null));
                List<ErrorVO> errorVOs = new ArrayList<>(1);
                errorVOs.add(errorVO);
                containerVO.setErrorVOs(errorVOs);
            }
        }
    }

    public List<ContainerVO> list() {
            try {

            } catch (Exception e) {

            }
            return null;
    }
}
