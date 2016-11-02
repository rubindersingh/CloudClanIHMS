package com.asu.cloudclan.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by rubinder on 10/4/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationVO extends RequestResponseVO {

    private String emailId;
    private String password;
    private String firstName;
    private String lastName;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
