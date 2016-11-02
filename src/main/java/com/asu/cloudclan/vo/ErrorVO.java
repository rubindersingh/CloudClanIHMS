package com.asu.cloudclan.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by rubinder on 10/4/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorVO {

    String fieldId;
    String message;

    public ErrorVO(String fieldId, String message) {
        this.fieldId = fieldId;
        this.message = message;
    }

    public ErrorVO(String message) {
        this.message = message;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
