package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.Container;
import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ContainerType;
import com.asu.cloudclan.service.util.ContainerGeneratorUtilService;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
public class ContainerService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    CassandraSessionService cassandraSessionService;
    @Autowired
    ContainerGeneratorUtilService containerGeneratorUtilService;

    public String createAndMap(String email, ContainerType type) {
        try {
            Session session = cassandraSessionService.getSession();
            String containerId = null;
            int counter = 0;
            while (true) {
                containerId = containerGeneratorUtilService.generate(email);
                ResultSet resultSet = session.execute(cassandraSessionService.CREATE_CONTAINER.bind(containerId, type.name()));
                if (resultSet.wasApplied()) {
                    break;
                }
                if(++counter==5) {
                    throw new Exception(messageSource.getMessage("no.unique.container.id",null,null));
                }
            }
            session.execute(cassandraSessionService.MAP_CONTAINER.bind(containerId, email, email, AccessType.A.name()));
            return containerId;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    public void map(String email, String containerId, AccessType type) {
        Session session = cassandraSessionService.getSession();
        ResultSet resultSet = session.execute(cassandraSessionService.MAP_CONTAINER.bind(containerId, email, email, type.name()));
    }

    public Long count() {
        Session session = cassandraSessionService.getSession();
        Long count = session.execute("SELECT COUNT(*) FROM container").one().getLong(0);
        return count;
    }
}
