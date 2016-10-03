package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.CloudContainer;
import com.asu.cloudclan.entity.cassandra.CloudContainerAccessor;
import com.asu.cloudclan.entity.cassandra.User;
import com.datastax.driver.core.Session;
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

    public void create(CloudContainer cloudContainer) {
        Mapper<CloudContainer> cloudContainerMapper = cassandraSessionService.getManager().mapper(CloudContainer.class);
        cloudContainerMapper.save(cloudContainer);
    }

    public Long count() {
        Session session = cassandraSessionService.getSession();
        Long count = session.execute("SELECT COUNT(*) FROM cloud_container").one().getLong(0);
        return count;
    }
}
