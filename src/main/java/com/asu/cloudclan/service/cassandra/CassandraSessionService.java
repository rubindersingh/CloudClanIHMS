package com.asu.cloudclan.service.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
@ApplicationScope
public class CassandraSessionService {

    private Session session;
    private Cluster cluster;
    private MappingManager manager;

    public CassandraSessionService() {
        cluster = Cluster.builder()
                .addContactPoint("192.168.1.4")
                .withPort(9042)
                .withClusterName("CloudClan")
                .build();
        session = cluster.connect("cloudclan");
        manager = new MappingManager(session);
    }

    public MappingManager getManager() {
        return manager;
    }
}
