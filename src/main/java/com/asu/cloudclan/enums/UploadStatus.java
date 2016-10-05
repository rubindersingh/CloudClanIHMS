package com.asu.cloudclan.enums;

/**
 * Created by rubinder on 10/4/16.
 */
public enum UploadStatus {
    QUEUED("Queued"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    SKIPPED("Skipped");

    String value;

    UploadStatus(String value) {
        this.value = value;
    }
}
