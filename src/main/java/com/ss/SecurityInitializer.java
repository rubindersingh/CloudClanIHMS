package com.ss;

import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by rubinder on 10/24/16.
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    public SecurityInitializer() {
        super(SecurityConfig.class, RedisSpringSessionConfig.class);
    }
}
