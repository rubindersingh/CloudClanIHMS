package com.asu.cloudclan.service.cassandra;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ubuntu on 10/2/16.
 */

public class BootstrapDataService implements InitializingBean {

    @Autowired
    ContainerService cloudContainerService;

    @Autowired
    UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bootstrapping data");
/*        Long count = cloudContainerService.count();
        if(count==0) {
            System.out.println("Creating Cloud Containers");
            Container cloudContainer = new Container(RandomStringUtils.randomAlphanumeric(10));
            cloudContainerService.create(cloudContainer);
            Container cloudContainer2 = new Container(RandomStringUtils.randomAlphanumeric(10));
            cloudContainerService.create(cloudContainer2);

            User user = new User("rsingh60@asu.edu","123456","Rubinder","Singh", cloudContainer.getCloudId());
            userService.create(user);

            User user2 = new User("uutkarsh@asu.edu","123456","Utkarsh","Prakash", cloudContainer2.getCloudId());
            userService.create(user2);
            System.out.println("Bootstrapped");
        }*/
    }
}
