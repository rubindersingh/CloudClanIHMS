package com.asu.cloudclan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by rubinder on 10/24/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    SpringSecurityAuthenticationService springSecurityAuthenticationService;

    @Bean
    public SpringSecurityAuthenticationService springSecurityAuthenticationService() {
        return new SpringSecurityAuthenticationService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/index*" ,"/register*","/css/**","/js/**", "/assets/**").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/images/{state}/{containerId}/**").access("@springSecurityAuthenticationService.isContainerAccessible(authentication,#containerId)")
                    .antMatchers("/images*//**", "/containers*//**", "/**")
                    .authenticated()
                    .and()
                    .httpBasic();
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(springSecurityAuthenticationService);
        authProvider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(authProvider);
    }

}
