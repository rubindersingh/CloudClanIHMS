package com.asu.cloudclan.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by rubinder on 10/4/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResponseVO {
    List<ErrorVO> errorVOs;
    Boolean status;
    Integer statusCode;

    public List<ErrorVO> getErrorVOs() {
        return errorVOs;
    }

    public void setErrorVOs(List<ErrorVO> errorVOs) {
        this.errorVOs = errorVOs;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
