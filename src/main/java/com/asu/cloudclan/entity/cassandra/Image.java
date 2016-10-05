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
    @PartitionKey
    @Column(name = "url")
    private String url;
    @Column(name = "cloud_name")
    private String cloudName;
    @Column(name = "metadata")
    @FrozenValue
    private Map<String, ImageMetadata> metadataMap;

    public Image(String url, String cloudName, Map<String, ImageMetadata> metadataMap) {
        this.url = url;
        this.cloudName = cloudName;
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

    public String getCloudName() {
        return cloudName;
    }

    public void setCloudName(String cloudName) {
        this.cloudName = cloudName;
    }

    public Map<String, ImageMetadata> getMetadataMap() {
        return metadataMap;
    }

    public void setMetadataMap(Map<String, ImageMetadata> metadataMap) {
        this.metadataMap = metadataMap;
    }
}
