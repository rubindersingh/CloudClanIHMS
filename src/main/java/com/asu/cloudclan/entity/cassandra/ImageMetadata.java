package com.asu.cloudclan.entity.cassandra;

import com.datastax.driver.mapping.annotations.*;

/**
 * Created by rubinder on 9/17/16.
 */
@UDT(name = "image_metadata", keyspace = "cloudclan")
public class ImageMetadata {

    @Field(name = "object_id")
    private String objectId;
    /*private Integer width;
    private Integer height;*/
    private Integer size;
    private String transformation;
    private String type;

    public ImageMetadata(String objectId, Integer size, String transformation, String type) {
        this.objectId = objectId;
        this.size = size;
        this.transformation = transformation;
        this.type = type;
    }

    public ImageMetadata() {

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

