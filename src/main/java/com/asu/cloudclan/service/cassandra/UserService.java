package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.User;
import com.datastax.driver.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
public class UserService {

    @Autowired
    CassandraSessionService cassandraSessionService;

    public void create(User user) {
        Mapper<User> userMapper = cassandraSessionService.getManager().mapper(User.class);
        userMapper.save(user);
    }

    public User find(User user) {
        Mapper<User> userMapper = cassandraSessionService.getManager().mapper(User.class);
        return null;
    }
}
