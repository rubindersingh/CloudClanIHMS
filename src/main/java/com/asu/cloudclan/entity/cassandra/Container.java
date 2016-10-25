package com.asu.cloudclan.entity.cassandra;

import com.asu.cloudclan.enums.ContainerType;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by rubinder on 9/17/16.
 */
@Table(name = "container", keyspace = "cloudclan")
public class Container {

    @PartitionKey
    @Column(name = "id")
    private String id;
    @Column(name = "type")
    private ContainerType type;

    /*Create container of specified type*/
    public Container(String id, ContainerType type) {
        this.id = id;
        this.type = type;
    }

    /*Create public container*/
    public Container(String id) {
        this(id, ContainerType.PUBLIC);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContainerType getType() {
        return type;
    }

    public void setType(ContainerType type) {
        this.type = type;
    }
}

