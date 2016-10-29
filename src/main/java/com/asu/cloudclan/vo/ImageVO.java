package com.asu.cloudclan.vo;

import com.asu.cloudclan.enums.UploadStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by rubinder on 10/4/16.
 */
public class ImageVO {

    String name;
    String url;
    UploadStatus status;
    String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UploadStatus getStatus() {
        return status;
    }

    public void setStatus(UploadStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
