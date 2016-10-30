package com.asu.cloudclan.entity.cassandra;


import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

/**
 * Created by rubinder on 9/16/16.
 */
@Table(name = "image_storage_use", keyspace = "cloudclan")
public class ImageStorageUse
{
    @PartitionKey(0)
    @Column(name = "container_id")
    private String containerId;
    @PartitionKey(1)
    @Column(name = "url")
    private String url;
    @PartitionKey(2)
    @Column(name = "transformation")
    private String transformation;
    @Column(name = "size")
    private Integer size;
    @Column(name = "ts")
    private UUID timeuuid;

    public ImageStorageUse() {
    }

    public ImageStorageUse(String containerId, String url, String transformation, Integer size, UUID timeuuid) {
        this.containerId = containerId;
        this.url = url;
        this.transformation = transformation;
        this.size = size;
        this.timeuuid = timeuuid;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public UUID getTimeuuid() {
        return timeuuid;
    }

    public void setTimeuuid(UUID timeuuid) {
        this.timeuuid = timeuuid;
    }
}
