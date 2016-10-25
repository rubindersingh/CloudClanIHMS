package com.asu.cloudclan.entity.cassandra;

import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ContainerType;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by rubinder on 9/17/16.
 */
@Table(name = "container_user", keyspace = "cloudclan")
public class ContainerUser {

    @PartitionKey(0)
    @Column(name = "container_id")
    private String containerId;
    @PartitionKey(1)
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "r_email_id")
    private String rEmailId; /*Redundant email column for search purpose*/
    @Column(name = "access_type")
    private AccessType accessType;

    public ContainerUser(String containerId, String emailId, AccessType accessType) {
        this.containerId = containerId;
        this.emailId = emailId;
        this.rEmailId = emailId;
        this.accessType = accessType;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}

