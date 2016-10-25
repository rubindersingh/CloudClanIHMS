package com.asu.cloudclan.service.util;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.security.MessageDigest;

/**
 * Created by rubinder on 10/11/16.
 */
@Service
@ApplicationScope
public class ContainerGeneratorUtilService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*Courtesy to stackoverflow and peter.petrov*/
    public String generate(String email) {
        try {
            String start = RandomStringUtils.randomAlphanumeric(10);
            String end = RandomStringUtils.randomAlphanumeric(10);
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(start).append(email).append(end);
            md.update(stringBuilder.toString().getBytes());
            byte[] bytes = md.digest();

            stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xff & bytes[i]);
                if (hex.length() == 1) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(hex);
            }
            return stringBuilder.toString().substring(0, 10);
        } catch (Exception e) {
            log.error("Error while generating container id;",e);
            return RandomStringUtils.randomAlphanumeric(10);
        }
    }
}
