package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.CloudContainer;
import com.asu.cloudclan.entity.cassandra.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ubuntu on 10/2/16.
 */
@Service
public class BootstrapDataService implements InitializingBean {

    @Autowired
    CloudContainerService cloudContainerService;

    @Autowired
    UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bootstrapping data");
        Long count = cloudContainerService.count();
        if(count==0) {
            System.out.println("Creating Cloud Containers");
            CloudContainer cloudContainer = new CloudContainer(RandomStringUtils.randomAlphanumeric(10));
            cloudContainerService.create(cloudContainer);
            CloudContainer cloudContainer2 = new CloudContainer(RandomStringUtils.randomAlphanumeric(10));
            cloudContainerService.create(cloudContainer2);

            User user = new User("rsingh60@asu.edu","123456","Rubinder","Singh", cloudContainer.getCloudId());
            userService.create(user);
9
            User user2 = new User("uutkarsh@asu.edu","123456","Utkarsh","Prakash", cloudContainer2.getCloudId());
            userService.create(user2);
            System.out.println("Bootstrapped");
        }
    }
}
