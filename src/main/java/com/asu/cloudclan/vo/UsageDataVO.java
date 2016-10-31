package com.asu.cloudclan.vo;

/**
 * Created by rubinder on 10/30/16.
 */
public class UsageDataVO {
    public int containers;
    public int images;
    public int transformations;
    public String imagesSize;
    public String uploadedSize;
    public String downloadedSize;

    public UsageDataVO(int containers, int images,  int transformations, String imagesSize, String uploadedSize, String downloadedSize) {
        this.containers = containers;
        this.images = images;
        this.imagesSize = imagesSize;
        this.uploadedSize = uploadedSize;
        this.downloadedSize = downloadedSize;
        this.transformations = transformations;
    }
}
