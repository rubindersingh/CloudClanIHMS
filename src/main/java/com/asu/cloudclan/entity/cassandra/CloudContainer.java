package com.asu.cloudclan.entity.cassandra;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by rubinder on 9/17/16.
 */
@Table(name = "cloud_container", keyspace = "cloudclan")
public class CloudContainer {

    @PartitionKey
    @Column(name = "cloud_id")
    private String cloudId;

    public CloudContainer(String cloudId) {
        this.cloudId = cloudId;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }
}

