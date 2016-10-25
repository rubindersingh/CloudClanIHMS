package com.asu.cloudclan.service.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
@ApplicationScope
public class CassandraSessionService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Session session;
    private Cluster cluster;
    private MappingManager manager;

    public PreparedStatement CREATE_CONTAINER;
    public PreparedStatement MAP_CONTAINER;

    @Autowired
    public CassandraSessionService(Environment env) {
        cluster = Cluster.builder()
                .addContactPoint(env.getProperty("cassandra.servers"))
                .withPort(env.getProperty("cassandra.port", Integer.class))
                .withClusterName("CloudClan")
                .build();
        session = cluster.connect("cloudclan");
        manager = new MappingManager(session);

        //Intializing prepared statements
        CREATE_CONTAINER = session.prepare("INSERT INTO container (id, type) VALUES (?, ?) IF NOT EXISTS");
        MAP_CONTAINER = session.prepare("INSERT INTO container_user (container_id, email_id, r_email_id, access_type) VALUES (?, ?, ?, ?)");
    }

    public MappingManager getManager() {
        return manager;
    }

    public Session getSession() {
        return session;
    }
}
