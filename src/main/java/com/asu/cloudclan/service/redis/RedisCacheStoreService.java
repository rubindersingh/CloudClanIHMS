package com.asu.cloudclan.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/29/16.
 */
@Service
public class RedisCacheStoreService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${instanceId}")
    private int instanceId;

    @Cacheable(value = "url", key = "#url")
    public String lookupImageObjectId(String url) {
        log.info("Web "+instanceId+": Entry not found in cache");
        return null;
    }

    @CachePut(value = "url", key = "#url")
    public String saveImageObjectId(String url, String objectId) {
        log.info("Web "+instanceId+": Saving object id in cache");
        return objectId;
    }
}
