package com.asu.cloudclan.vo;

/**
 * Created by rubinder on 10/26/16.
 */
public class ImageMetadataVO {
    private String objectId;
    private String url;
    private Integer uploadedSize;
    private Integer storedSize;
    private Integer downloadSize;
    private String type;
    private String transformation;
    private Integer transformed;
    private String containerId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUploadedSize() {
        return uploadedSize;
    }

    public void setUploadedSize(Integer uploadedSize) {
        this.uploadedSize = uploadedSize;
    }

    public Integer getStoredSize() {
        return storedSize;
    }

    public void setStoredSize(Integer storedSize) {
        this.storedSize = storedSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public Integer getTransformed() {
        return transformed;
    }

    public void setTransformed(Integer transformed) {
        this.transformed = transformed;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Integer getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(Integer downloadSize) {
        this.downloadSize = downloadSize;
    }
}
