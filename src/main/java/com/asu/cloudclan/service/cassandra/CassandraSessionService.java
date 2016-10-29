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

    private PreparedStatement CREATE_CONTAINER;
    private PreparedStatement MAP_CONTAINER;
    private PreparedStatement SAVE_IMAGE;
    private PreparedStatement SAVE_TRANSFORMATION;

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
        CREATE_CONTAINER = session.prepare("INSERT INTO container (id, name, type) VALUES (?, ?, ?) IF NOT EXISTS");
        MAP_CONTAINER = session.prepare("INSERT INTO user_container (email_id, container_id, r_container_id, access_type) VALUES (?, ?, ?, ?)");

        SAVE_IMAGE = session.prepare("INSERT INTO image (container_id, url, metadata) VALUES (?, ?, ?)");
        SAVE_TRANSFORMATION = session.prepare("UPDATE image SET metadata = metadata + ? WHERE container_id = ? AND url=?");
    }

    public MappingManager getManager() {
        return manager;
    }

    public Session getSession() {
        return session;
    }

    public PreparedStatement getCreateContainerStatement() {
        return CREATE_CONTAINER;
    }

    public PreparedStatement getMapContainerStatement() {
        return MAP_CONTAINER;
    }

    public PreparedStatement getSaveImageStatement() {
        return SAVE_IMAGE;
    }

    public PreparedStatement getSaveTransformationStatement() {
        return SAVE_TRANSFORMATION;
    }
}
