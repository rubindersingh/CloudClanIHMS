package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.Container;
import com.asu.cloudclan.entity.cassandra.User;
import com.asu.cloudclan.entity.cassandra.UserContainer;
import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ContainerType;
import com.asu.cloudclan.service.util.ContainerGeneratorUtilService;
import com.asu.cloudclan.vo.ContainerVO;
import com.asu.cloudclan.vo.ErrorVO;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
public class ContainerCoreService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    CassandraSessionService cassandraSessionService;
    @Autowired
    ContainerGeneratorUtilService containerGeneratorUtilService;

    public void createAndMap(String email, ContainerVO containerVO) {
        try {
            Session session = cassandraSessionService.getSession();
            String containerId = null;
            int counter = 0;
            while (true) {
                containerId = containerGeneratorUtilService.generate(email);
                ResultSet resultSet = session.execute(cassandraSessionService.getCreateContainerStatement().bind(containerId, containerVO.getName(), containerVO.getType()));
                if (resultSet.wasApplied()) {
                    break;
                }
                if(++counter==5) {
                    throw new Exception(messageSource.getMessage("no.unique.container.id",null,null));
                }
            }
            session.execute(cassandraSessionService.getMapContainerStatement().bind(email, containerId, containerId, AccessType.A.name()));
            containerVO.setId(containerId);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            ErrorVO errorVO = new ErrorVO(messageSource.getMessage("server.error",null,null));
            List<ErrorVO> errorVOs = new ArrayList<>(1);
            errorVOs.add(errorVO);
            containerVO.setErrorVOs(errorVOs);
        }
    }

    public boolean map(ContainerVO containerVO) {
        Session session = cassandraSessionService.getSession();
        ResultSet resultSet = session.execute(cassandraSessionService.getMapContainerStatement().bind(containerVO.getEmailId(), containerVO.getId(), containerVO.getId(), containerVO.getType()));
        return resultSet.wasApplied();
    }

    public Long count() {
        Session session = cassandraSessionService.getSession();
        Long count = session.execute("SELECT COUNT(*) FROM container").one().getLong(0);
        return count;
    }

    public Container find(String containerId) {
        Mapper<Container> containerMapper = cassandraSessionService.getManager().mapper(Container.class);
        return containerMapper.get(containerId);
    }

    public boolean isPublic(String containerId) {
        Mapper<Container> containerMapper = cassandraSessionService.getManager().mapper(Container.class);
        Container container = containerMapper.get(containerId);
        if(container != null) {
            return ContainerType.valueOf(container.getType()) == ContainerType.PUBLIC;
        }
        return false;
    }

    public UserContainer find(String emailId, String containerId) {
        Mapper<UserContainer> userContainerMapper = cassandraSessionService.getManager().mapper(UserContainer.class);
        return userContainerMapper.get(emailId, containerId);
    }

    public boolean hasAccess(String emailId, String containerId) {
        Mapper<UserContainer> userContainerMapper = cassandraSessionService.getManager().mapper(UserContainer.class);
        UserContainer userContainer = userContainerMapper.get(emailId, containerId);
        if(userContainer != null) {
            return AccessType.hasReadAccess(userContainer.getAccessType());
        }
        return false;
    }
}
