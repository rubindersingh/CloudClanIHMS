package com.asu.cloudclan.service.swift;

import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by ubuntu on 10/4/16.
 */
//@Service
public class SwiftStorageCoreService {

    @Autowired
    Account account;

    @Async
    public void uploadObjectAync(String objectId, InputStream inputStream) {
        Container container = account.getContainer("images");
        StoredObject object = container.getObject(objectId);
        object.uploadObject(inputStream);
    }

    public void uploadObject(String objectId, InputStream inputStream) {
        Container container = account.getContainer("images");
        StoredObject object = container.getObject(objectId);
        object.uploadObject(inputStream);
    }

    public InputStream downloadObject(String objectId) {
        Container container = account.getContainer("images");
        StoredObject object = container.getObject(objectId);
        return object.downloadObjectAsInputStream();
    }
}
