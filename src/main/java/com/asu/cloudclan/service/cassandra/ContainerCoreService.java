package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.Container;
import com.asu.cloudclan.entity.cassandra.User;
import com.asu.cloudclan.entity.cassandra.UserContainer;
import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ContainerType;
import com.asu.cloudclan.service.util.ContainerGeneratorUtilService;
import com.asu.cloudclan.util.SizeUtil;
import com.asu.cloudclan.vo.ContainerVO;
import com.asu.cloudclan.vo.ErrorVO;
import com.asu.cloudclan.vo.HomeVO;
import com.asu.cloudclan.vo.UsageDataVO;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
public class ContainerCoreService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    CassandraSessionService cassandraSessionService;
    @Autowired
    ContainerGeneratorUtilService containerGeneratorUtilService;

    public void createAndMap(String email, ContainerVO containerVO) {
        try {
            Session session = cassandraSessionService.getSession();
            String containerId = null;
            int counter = 0;
            while (true) {
                containerId = containerGeneratorUtilService.generate(email);
                ResultSet resultSet = session.execute(cassandraSessionService.getCreateContainerStatement().bind(containerId, containerVO.getName(), containerVO.getType()));
                if (resultSet.wasApplied()) {
                    break;
                }
                if(++counter==5) {
                    throw new Exception(messageSource.getMessage("no.unique.container.id",null,null));
                }
            }
            session.execute(cassandraSessionService.getMapContainerStatement().bind(email, containerId, containerId, AccessType.A.name()));
            containerVO.setId(containerId);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            ErrorVO errorVO = new ErrorVO(messageSource.getMessage("server.error",null,null));
            List<ErrorVO> errorVOs = new ArrayList<>(1);
            errorVOs.add(errorVO);
            containerVO.setErrorVOs(errorVOs);
        }
    }

    public boolean map(ContainerVO containerVO) {
        Session session = cassandraSessionService.getSession();
        ResultSet resultSet = session.execute(cassandraSessionService.getMapContainerStatement().bind(containerVO.getEmailId(), containerVO.getId(), containerVO.getId(), containerVO.getType()));
        return resultSet.wasApplied();
    }

    public Long count() {
        Session session = cassandraSessionService.getSession();
        Long count = session.execute("SELECT COUNT(*) FROM container").one().getLong(0);
        return count;
    }

    public Container find(String containerId) {
        Mapper<Container> containerMapper = cassandraSessionService.getManager().mapper(Container.class);
        return containerMapper.get(containerId);
    }

    @Cacheable(value = "container", key = "#containerId")
    public boolean isPublic(String containerId) {
        Mapper<Container> containerMapper = cassandraSessionService.getManager().mapper(Container.class);
        Container container = containerMapper.get(containerId);
        if(container != null) {
            return ContainerType.valueOf(container.getType()) == ContainerType.PUBLIC;
        }
        return false;
    }

    public UserContainer find(String emailId, String containerId) {
        Mapper<UserContainer> userContainerMapper = cassandraSessionService.getManager().mapper(UserContainer.class);
        return userContainerMapper.get(emailId, containerId);
    }

    @Cacheable(value = "container", key = "{#emailId, #containerId}")
    public boolean hasAccess(String emailId, String containerId) {
        Mapper<UserContainer> userContainerMapper = cassandraSessionService.getManager().mapper(UserContainer.class);
        UserContainer userContainer = userContainerMapper.get(emailId, containerId);
        if(userContainer != null) {
            return AccessType.hasReadAccess(userContainer.getAccessType());
        }
        return false;
    }

    public HomeVO getUsageAndContainers(String emailId) {
        HomeVO homeVO = new HomeVO();
        Session session = cassandraSessionService.getSession();
        ResultSet resultSet = session.execute(cassandraSessionService.getAllUserContainers().bind(emailId));
        Iterator<Row> rowIterator = resultSet.iterator();
        Map<String, ContainerVO> map = new LinkedHashMap<>();
        StringBuilder authoredContainers = new StringBuilder();
        StringBuilder containerIds = new StringBuilder();
        int authoredContainerSize = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String containerId = row.getString(0);
            String accessType = row.getString(1);
            if(accessType.equals(AccessType.A.name())) {
                authoredContainers.append("'"+containerId+"',");
                authoredContainerSize++;
            }
            containerIds.append("'"+containerId+"',");
            ContainerVO containerVO = new ContainerVO(containerId,null,null,null,accessType);
            map.put(containerId, containerVO);
        }
        if(containerIds.length()>0) {
            containerIds.deleteCharAt(containerIds.length()-1);

            resultSet = session.execute("SELECT * FROM container WHERE id IN ("+containerIds+")");
            rowIterator = resultSet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String containerId = row.getString(0);
                String name = row.getString(1);
                String type = row.getString(2);

                ContainerVO containerVO = map.get(containerId);
                containerVO.setName(name);
                containerVO.setType(type);
            }
        }
        if(authoredContainers.length()>0) {
            authoredContainers.deleteCharAt(authoredContainers.length()-1);

            resultSet = session.execute("SELECT sum(trans),sum(upload_size),sum(download_size) FROM image_service_use WHERE container_id IN ("+authoredContainers+")");
            Row row = resultSet.one();
            Integer transformationCount = row.getInt(0);
            Integer uploadSize = row.getInt(1);
            Integer downloadSize = row.getInt(2);

            resultSet = session.execute("SELECT sum(size), totalcost(size, toTimestamp(ts)) from image_storage_use WHERE container_id IN ("+authoredContainers+")");
            row = resultSet.one();
            Integer storedSize = row.getInt(0);
            Double storedSizeTime = row.getDouble(1);

            resultSet = session.execute("SELECT count(*) FROM image WHERE container_id IN ("+authoredContainers+")");
            row = resultSet.one();
            Long images = row.getLong(0);

            UsageDataVO usageDataVO = new UsageDataVO(authoredContainerSize, images.intValue(), transformationCount, SizeUtil.calculateSize(storedSize),
                    SizeUtil.calculateSize(uploadSize), SizeUtil.calculateSize(downloadSize), SizeUtil.calculateTimeSize(storedSizeTime));

            homeVO.setUsageDataVO(usageDataVO);
        } else {
            UsageDataVO usageDataVO = new UsageDataVO(authoredContainerSize, 0, 0, SizeUtil.calculateSize(0),
                    SizeUtil.calculateSize(0), SizeUtil.calculateSize(0), SizeUtil.calculateTimeSize(0d));

            homeVO.setUsageDataVO(usageDataVO);
        }
        homeVO.setContainerVOs(new ArrayList<>(map.values()));
        return homeVO;
    }
}
