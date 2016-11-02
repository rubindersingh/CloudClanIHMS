package com.asu.cloudclan.service.swift;

import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * Created by rubinder on 10/4/16.
 */
@Service
public class SwiftStorageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SwiftStorageCoreService swiftStorageCoreService;

    public String uploadObject(InputStream inputStream) {
        String objectId = UUID.randomUUID().toString();
        swiftStorageCoreService.uploadObjectAync(objectId, inputStream);
        return objectId;
    }

    public String uploadObject(String objectId, InputStream inputStream) {
        try {
            swiftStorageCoreService.uploadObject(objectId, inputStream);
            return objectId;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public byte[] downloadObject(String objectId) {
        try {
            return swiftStorageCoreService.downloadObject(objectId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
