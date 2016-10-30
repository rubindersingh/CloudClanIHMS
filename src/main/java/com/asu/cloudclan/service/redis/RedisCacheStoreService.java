package com.asu.cloudclan.service.redis;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/29/16.
 */
@Service
public class RedisCacheStoreService {


    @Cacheable(value = "url", key = "#url")
    public String lookupImageObjectId(String url) {
        return null;
    }

    @CachePut(value = "url", key = "#url")
    public String saveImageObjectId(String url, String objectId) {
        return objectId;
    }
}
