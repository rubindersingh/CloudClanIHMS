package com.asu.cloudclan.entity.cassandra;


import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.FrozenValue;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Map;

/**
 * Created by rubinder on 9/16/16.
 */
@Table(name = "image", keyspace = "cloudclan")
public class Image
{
    @PartitionKey(0)
    @Column(name = "container_id")
    private String containerId;
    @PartitionKey(1)
    @Column(name = "url")
    private String url;
/*    @Column(name = "total_size")
    private Integer totalSize;
    @Column(name = "total_trans")
    private Integer totalTrans;*/
    @Column(name = "metadata")
    @FrozenValue
    private Map<String, ImageMetadata> metadataMap;

/*    public Image(String url, String containerId, Integer totalSize, Integer totalTrans, Map<String, ImageMetadata> metadataMap) {
        this.url = url;
        this.containerId = containerId;
        this.totalSize = totalSize;
        this.totalTrans = totalTrans;
        this.metadataMap = metadataMap;
    }*/

    public Image(String containerId, String url, Map<String, ImageMetadata> metadataMap) {
        this.url = url;
        this.containerId = containerId;
        this.metadataMap = metadataMap;
    }

    public Image() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Map<String, ImageMetadata> getMetadataMap() {
        return metadataMap;
    }

    public void setMetadataMap(Map<String, ImageMetadata> metadataMap) {
        this.metadataMap = metadataMap;
    }
}
