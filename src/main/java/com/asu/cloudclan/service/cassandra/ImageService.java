package com.asu.cloudclan.service.cassandra;

import com.asu.cloudclan.entity.cassandra.Image;
import com.asu.cloudclan.entity.cassandra.ImageMetadata;
import com.asu.cloudclan.entity.cassandra.User;
import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ContainerType;
import com.asu.cloudclan.service.util.ContainerGeneratorUtilService;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.crossstore.HashMapChangeSet;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rubinder on 10/2/16.
 */
@Service
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    CassandraSessionService cassandraSessionService;

    public void save(Image image) {
        Mapper<Image> imageMapper = cassandraSessionService.getManager().mapper(Image.class);
        imageMapper.save(image);
    }

    public Image get(String url) {
        Mapper<Image> imageMapper = cassandraSessionService.getManager().mapper(Image.class);
        return imageMapper.get(url);
    }

    public void addTransformation(String url, ImageMetadata imageMetadata) {
        Session session = cassandraSessionService.getSession();
        MappingManager mappingManager = cassandraSessionService.getManager();
        TypeCodec<ImageMetadata> imageMetadataTypeCodec = mappingManager.udtCodec(ImageMetadata.class);
        PreparedStatement preparedStatement = session.prepare("UPDATE image SET metadata = metadata + ?" +
                " WHERE url=?");
        Map<String, ImageMetadata> map = new HashMap<>();
        map.put(imageMetadata.getTransformation(),imageMetadata);
        session.execute(preparedStatement.bind(map,url));
    }

    public void doTransformation() {
        Session session = cassandraSessionService.getSession();
        PreparedStatement preparedStatement = session.prepare("select group_and_total(url,size),group_and_total(url,trans),group_and_total(url,upload_size) from image_stats where container_id = 'vhhkkjhj' AND url IN ('/home/pic.jpg', '/home/pic1.jpg','/home/pic2.jpg')");
        ResultSet resultSet = session.execute(preparedStatement.bind());
        Row row = resultSet.one();
        Map map1 = row.getMap(0,String.class, Integer.class);
        Map map2 = row.getMap(1,String.class, Integer.class);
        Map map3 = row.getMap(2,String.class, Integer.class);
        System.out.println();
        return;
    }

    public Long count() {
        Session session = cassandraSessionService.getSession();
        Long count = session.execute("SELECT COUNT(*) FROM container").one().getLong(0);
        return count;
    }
}
