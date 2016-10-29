package com.asu.cloudclan.vo;

/**
 * Created by rubinder on 10/26/16.
 */
public class ImageMetadataVO {
    private String objectId;
    private String url;
    private Long uploadedSize;
    private Long storedSize;
    private Long downloadSize;
    private String type;
    private String transformation;
    private Boolean transformed;
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

    public Long getUploadedSize() {
        return uploadedSize;
    }

    public void setUploadedSize(Long uploadedSize) {
        this.uploadedSize = uploadedSize;
    }

    public Long getStoredSize() {
        return storedSize;
    }

    public void setStoredSize(Long storedSize) {
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

    public Boolean getTransformed() {
        return transformed;
    }

    public void setTransformed(Boolean transformed) {
        this.transformed = transformed;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Long getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(Long downloadSize) {
        this.downloadSize = downloadSize;
    }
}
