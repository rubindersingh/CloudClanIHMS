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
    private PreparedStatement SAVE_IMAGE_SERVICE_USE;
    private PreparedStatement SAVE_IMAGE_STORAGE_USE;
    private PreparedStatement GET_ALL_USER_CONTAINERS;
    private PreparedStatement GET_CONTAINERS_BY_ID_LIST;
    private PreparedStatement GET_SERVICE_USAGE_BY_CONTAINER_ID_LIST;
    private PreparedStatement GET_STORAGE_USAGE_BY_CONTAINER_ID_LIST;

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

        SAVE_IMAGE_SERVICE_USE = session.prepare("INSERT INTO image_service_use (container_id, url, ts, trans, upload_size, download_size) VALUES (?, ?, ?, ?, ?, ?)");
        SAVE_IMAGE_STORAGE_USE = session.prepare("INSERT INTO image_storage_use (container_id, url, transformation, size, ts) VALUES (?, ?, ?, ?, ?)");

        GET_ALL_USER_CONTAINERS = session.prepare("SELECT container_id, access_type FROM user_container WHERE email_id = ?");
        GET_CONTAINERS_BY_ID_LIST = session.prepare("SELECT * FROM container WHERE id IN ?");
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

    public PreparedStatement getSaveImageServiceUseStatement() {
        return SAVE_IMAGE_SERVICE_USE;
    }

    public PreparedStatement getSaveImageStorageUseStatement() {
        return SAVE_IMAGE_STORAGE_USE;
    }

    public PreparedStatement getAllUserContainers() {
        return GET_ALL_USER_CONTAINERS;
    }

    public PreparedStatement getContainersByIdList() {
        return GET_CONTAINERS_BY_ID_LIST;
    }
}
