package com.asu.cloudclan.entity.cassandra;


import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.FrozenValue;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Map;
import java.util.UUID;

/**
 * Created by rubinder on 9/16/16.
 */
@Table(name = "image_service_use", keyspace = "cloudclan")
public class ImageServiceUse
{
    @PartitionKey(0)
    @Column(name = "container_id")
    private String containerId;
    @PartitionKey(1)
    @Column(name = "url")
    private String url;
    @PartitionKey(2)
    @Column(name = "ts")
    private UUID timeuuid;
    @Column(name = "trans")
    private Integer trans;
    @Column(name = "upload_size")
    private Integer uploadSize;
    @Column(name = "download_size")
    private Integer downloadSize;

    public ImageServiceUse() {
    }

    public ImageServiceUse(String containerId, String url, UUID timeuuid, Integer trans, Integer uploadSize, Integer downloadSize) {
        this.containerId = containerId;
        this.url = url;
        this.timeuuid = timeuuid;
        this.trans = trans;
        this.uploadSize = uploadSize;
        this.downloadSize = downloadSize;
    }

    public UUID getTimeuuid() {
        return timeuuid;
    }

    public void setTimeuuid(UUID timeuuid) {
        this.timeuuid = timeuuid;
    }

    public Integer getTrans() {
        return trans;
    }

    public void setTrans(Integer trans) {
        this.trans = trans;
    }

    public Integer getUploadSize() {
        return uploadSize;
    }

    public void setUploadSize(Integer uploadSize) {
        this.uploadSize = uploadSize;
    }

    public Integer getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(Integer downloadSize) {
        this.downloadSize = downloadSize;
    }
}
