package com.asu.cloudclan.service.swift;

import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * Created by rubinder on 10/4/16.
 */
@Service
public class SwiftStorageService {

    @Autowired
    SwiftStorageCoreService swiftStorageCoreService;

    public String uploadObject(InputStream inputStream) {
        String objectId = UUID.randomUUID().toString();
        swiftStorageCoreService.uploadObjectAync(objectId, inputStream);
        return objectId;
    }

    public InputStream downloadObject(String objectId) {
        return swiftStorageCoreService.downloadObject(objectId);
    }
}
