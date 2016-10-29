package com.asu.cloudclan.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

/**
 * Created by rubinder on 10/27/16.
 */
public class JsonUtil {
    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    public static String getJsonString(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }
}
