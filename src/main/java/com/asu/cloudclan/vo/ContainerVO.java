package com.asu.cloudclan.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by rubinder on 10/26/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContainerVO extends RequestResponseVO {
    private String id;
    private String type;
    private String name;
    private String emailId;
    private String accessType;

    public ContainerVO(String id, String type, String name, String emailId, String accessType) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.emailId = emailId;
        this.accessType = accessType;
    }

    public ContainerVO(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public ContainerVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
}
