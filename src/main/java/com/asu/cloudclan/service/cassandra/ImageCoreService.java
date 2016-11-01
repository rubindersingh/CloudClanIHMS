package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.Image;
import com.asu.cloudclan.vo.ImageMetadataVO;
import com.asu.cloudclan.vo.UploadVO;
import com.datastax.driver.core.*;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
public class ImageCoreService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    CassandraSessionService cassandraSessionService;

    public void saveUploadInfo(UploadVO uploadVO) {
        String containerId = uploadVO.getContainerId();
        List<ImageMetadataVO> imageMetadataVOs = uploadVO.getImageMetadataVOs();
        for (ImageMetadataVO imageMetadataVO : imageMetadataVOs) {
            Map<String, String> map = new HashMap<>();
            map.put(imageMetadataVO.getTransformation(), imageMetadataVO.getObjectId());
            Session session = cassandraSessionService.getSession();
            ResultSet resultSet = session.execute(cassandraSessionService.getSaveImageStatement().bind(containerId, imageMetadataVO.getUrl(), map));
            resultSet = session.execute(cassandraSessionService.getSaveImageServiceUseStatement().bind(containerId, imageMetadataVO.getUrl(), UUIDs.timeBased(), imageMetadataVO.getTransformed(), imageMetadataVO.getUploadedSize(), imageMetadataVO.getDownloadSize()));
            resultSet = session.execute(cassandraSessionService.getSaveImageStorageUseStatement().bind(containerId, imageMetadataVO.getUrl(), imageMetadataVO.getTransformation(), imageMetadataVO.getStoredSize(), UUIDs.timeBased()));
        }
    }

    public void saveDownloadInfo(ImageMetadataVO imageMetadataVO) {
        Session session = cassandraSessionService.getSession();
        if(imageMetadataVO.getObjectId() != null) {
            Map<String, String> map = new HashMap<>();
            map.put(imageMetadataVO.getTransformation(), imageMetadataVO.getObjectId());
            ResultSet resultSet = session.execute(cassandraSessionService.getSaveTransformationStatement().bind(map, imageMetadataVO.getContainerId(), imageMetadataVO.getUrl()));
            resultSet = session.execute(cassandraSessionService.getSaveImageStorageUseStatement().bind(imageMetadataVO.getContainerId(), imageMetadataVO.getUrl(), imageMetadataVO.getTransformation(), imageMetadataVO.getStoredSize(), UUIDs.timeBased()));
        }
        ResultSet resultSet = session.execute(cassandraSessionService.getSaveImageServiceUseStatement().bind(imageMetadataVO.getContainerId(), imageMetadataVO.getUrl(), UUIDs.timeBased(), imageMetadataVO.getTransformed(), imageMetadataVO.getUploadedSize(), imageMetadataVO.getDownloadSize()));
    }

    public void save(Image image) {
        Mapper<Image> imageMapper = cassandraSessionService.getManager().mapper(Image.class);
        imageMapper.save(image);
    }

    public Image get(String container, String url) {
        Mapper<Image> imageMapper = cassandraSessionService.getManager().mapper(Image.class);
        return imageMapper.get(container, url);
    }

}
