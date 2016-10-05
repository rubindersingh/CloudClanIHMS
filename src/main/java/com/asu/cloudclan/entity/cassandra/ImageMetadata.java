package com.asu.cloudclan.entity.cassandra;

import com.datastax.driver.mapping.annotations.*;

/**
 * Created by rubinder on 9/17/16.
 */
@UDT(name = "image_metadata", keyspace = "cloudclan")
public class ImageMetadata {

    @Field(name = "object_id")
    private String objectId;
    private Integer width;
    private Integer height;
    private Integer size;
    private String transformation;
    private String type;

    public ImageMetadata(String objectId, Integer width, Integer height, Integer size, String transformation, String type) {
        this.objectId = objectId;
        this.width = width;
        this.height = height;
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

