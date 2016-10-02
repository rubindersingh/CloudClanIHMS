package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.CloudContainer;
import com.asu.cloudclan.entity.cassandra.User;
import com.datastax.driver.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
public class CloudContainerService {

    @Autowired
    CassandraSessionService cassandraSessionService;

    public void create() {
        Mapper<CloudContainer> cloudContainerMapper = cassandraSessionService.getManager().mapper(CloudContainer.class);
        CloudContainer cloudContainer = new CloudContainer("abcdefghlk");
        cloudContainerMapper.save(cloudContainer);
    }
}
