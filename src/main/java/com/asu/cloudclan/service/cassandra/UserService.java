package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.User;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    CassandraSessionService cassandraSessionService;

    public void create(User user) {
        Mapper<User> userMapper = cassandraSessionService.getManager().mapper(User.class);
        userMapper.save(user);
    }

    public User find(String emailId) {
        Mapper<User> userMapper = cassandraSessionService.getManager().mapper(User.class);
        return userMapper.get(emailId);
    }
}
