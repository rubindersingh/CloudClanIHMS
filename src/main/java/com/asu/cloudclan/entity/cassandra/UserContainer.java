package com.asu.cloudclan.entity.cassandra;

import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ContainerType;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by rubinder on 9/17/16.
 */
@Table(name = "user_container", keyspace = "cloudclan")
public class UserContainer {

    @PartitionKey(0)
    @Column(name = "email_id")
    private String emailId;
    @PartitionKey(1)
    @Column(name = "container_id")
    private String containerId;
    @Column(name = "r_container_id")
    private String rContainerId; /*Redundant container column for metadata purpose*/
    @Column(name = "access_type")
    private String accessType;

    public UserContainer(String containerId, String emailId, String accessType) {
        this.containerId = containerId;
        this.emailId = emailId;
        this.rContainerId = containerId;
        this.accessType = accessType;
    }

    public UserContainer() {

    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getrContainerId() {
        return rContainerId;
    }

    public void setrContainerId(String rContainerId) {
        this.rContainerId = rContainerId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
}

