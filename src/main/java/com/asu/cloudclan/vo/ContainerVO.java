package com.asu.cloudclan.vo;

/**
 * Created by rubinder on 10/26/16.
 */
public class ContainerVO extends RequestResponseVO {
    private String id;
    private String type;
    private String name;
    private String emailId;

    public ContainerVO(String id, String type, String name, String emailId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.emailId = emailId;
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
}
