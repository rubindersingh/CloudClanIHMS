package com.asu.cloudclan.app.conf;

import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by rubinder on 10/3/16.
 */
//@Configuration
public class SwiftStorageConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public AccountConfig accountConfig() {
        AccountConfig config = new AccountConfig();
        config.setUsername("admin:admin");
        config.setPassword("admin");
        config.setAuthUrl("http://127.0.0.1:8080/auth/v1.0");
        config.setAuthenticationMethod(AuthenticationMethod.TEMPAUTH);
        return config;
    }

    @Bean
    public Account account(AccountConfig accountConfig) {
        return new AccountFactory(accountConfig).createAccount();
    }
}
