package com.asu.cloudclan.vo;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by rubinder on 10/4/16.
 */
public class UploadVO extends RequestResponseVO {

    String containerId;
    Boolean keepOriginal;
    List<MultipartFile> files;
    List<ImageVO> imageVOs;
    List<ImageMetadataVO> imageMetadataVOs;

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Boolean getKeepOriginal() {
        return keepOriginal;
    }

    public void setKeepOriginal(Boolean keepOriginal) {
        this.keepOriginal = keepOriginal;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<ImageVO> getImageVOs() {
        return imageVOs;
    }

    public void setImageVOs(List<ImageVO> imageVOs) {
        this.imageVOs = imageVOs;
    }

    public List<ImageMetadataVO> getImageMetadataVOs() {
        return imageMetadataVOs;
    }

    public void setImageMetadataVOs(List<ImageMetadataVO> imageMetadataVOs) {
        this.imageMetadataVOs = imageMetadataVOs;
    }
}
