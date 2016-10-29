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
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    /*Create container of specified type*/
    public Container(String id, String name, String type) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    /*Create public container*/
    public Container(String id, String name) {
        this(id, name, ContainerType.PUBLIC.name());
    }

    public Container() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

