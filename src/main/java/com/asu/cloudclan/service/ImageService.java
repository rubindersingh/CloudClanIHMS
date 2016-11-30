package com.asu.cloudclan.service;

import com.asu.cloudclan.entity.cassandra.Image;
import com.asu.cloudclan.enums.ImageFormat;
import com.asu.cloudclan.service.cassandra.ImageCoreService;
import com.asu.cloudclan.service.core.CoreTransformationService;
import com.asu.cloudclan.service.rabbitmq.RabbitMQSenderService;
import com.asu.cloudclan.service.redis.RedisCacheStoreService;
import com.asu.cloudclan.service.swift.SwiftStorageService;
import com.asu.cloudclan.vo.ErrorVO;
import com.asu.cloudclan.vo.ImageMetadataVO;
import com.asu.cloudclan.vo.TransformationVO;
import org.apache.commons.io.IOUtils;
import org.javaswift.joss.command.impl.object.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rubinder on 10/25/16.
 */
@Service
public class ImageService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${instanceId}")
    private int instanceId;

    @Autowired
    private MessageSource messageSource;
    @Autowired()
    private ImageCoreService imageCoreService;
    @Autowired
    private RabbitMQSenderService rabbitMQSenderService;
    @Autowired
    SwiftStorageService swiftStorageService;
    @Autowired
    CoreTransformationService coreTransformationService;
    @Autowired
    RedisCacheStoreService redisCacheStoreService;

    public InputStream getImage(String containerId, String state, String imageUrl, TransformationVO transformationVO) {
        try {
            List<ErrorVO> errorVOs = new ArrayList<>();
            int dotLastIndex = imageUrl.lastIndexOf(".");
            String extension = null;
            String urlWithoutExt = null;
            if (dotLastIndex != -1 && dotLastIndex != (imageUrl.length()-1)) {
                extension = imageUrl.substring(dotLastIndex+1);
                urlWithoutExt = imageUrl.substring(0,dotLastIndex);
            } else if (dotLastIndex != -1) {
                urlWithoutExt = imageUrl.substring(0,dotLastIndex);
            } else {
                urlWithoutExt = imageUrl;
            }

            String transformation = transformationVO.toString();
            boolean transform = true;
            if(transformation.length() == 0) {
                transformation = "";
                transform = false;
            }

            //Look up for container+URL+transformation in redis first else proceed to next lines
            // If not found, first validate transformation
            log.info("Web "+instanceId+": Looking for resource object id in cache");
            String objectId = redisCacheStoreService.lookupImageObjectId(containerId+urlWithoutExt+transformation);
            boolean transformationFound = false;
            if(objectId == null) {
                if(!transformationVO.validateAndConvert()) {
                    ErrorVO errorVO = new ErrorVO(messageSource.getMessage("invalid.image.op.params",null,null));
                    errorVOs.add(errorVO);
                    transformationVO.errorVOs = errorVOs;
                    return null;
                }
                log.info("Web "+instanceId+": Looking for resource in main database");
                Image image = imageCoreService.get(containerId, urlWithoutExt);
                if(image == null) {
                    log.info("Web "+instanceId+": Resource not found in main DB");
                    ErrorVO errorVO = new ErrorVO(messageSource.getMessage("invalid.image.url",null,null));
                    errorVOs.add(errorVO);
                    transformationVO.errorVOs = errorVOs;
                    return null;
                }
                Map<String, String> metadataMap = image.getMetadataMap();

                if(!transform || !metadataMap.containsKey(transformation)) {
                    if(metadataMap.containsKey("OPTIMIZED")) {
                        objectId = metadataMap.get("OPTIMIZED");
                    } else {
                        objectId = metadataMap.get("ORIGINAL");
                    }
                } else {
                    objectId = metadataMap.get(transformation);
                    transformationFound = true;
                    log.info("Web "+instanceId+": Transformation found from main database");
                    redisCacheStoreService.saveImageObjectId(containerId+urlWithoutExt+transformation, objectId);
                }
            }

            log.info("Web "+instanceId+": Downloading resource from swift object store.");
            byte[] bytes = swiftStorageService.downloadObject(objectId);
            if(bytes == null) {
                return null; //As object not found
            }
            InputStream inputStream = new ByteArrayInputStream(bytes);

            ImageMetadataVO imageMetadataVO = new ImageMetadataVO();
            imageMetadataVO.setContainerId(containerId);
            imageMetadataVO.setUrl(urlWithoutExt);
            if(transform && !transformationFound) {
                //Run required transform here and return
                log.info("Web "+instanceId+": Transforming object as per provided parameters");
                inputStream = coreTransformationService.transform(inputStream, transformationVO);
                imageMetadataVO.setDownloadSize(inputStream.available());

                imageMetadataVO.setTransformation(transformation);
                imageMetadataVO.setTransformed(1);
                if(state.equals("p")) {
                    imageMetadataVO.setStoredSize(inputStream.available());
                    imageMetadataVO.setType(ImageFormat.JPG.name());
                    try {
                        bytes = IOUtils.toByteArray(inputStream);
                        inputStream = new ByteArrayInputStream(bytes);
                        swiftStorageService.uploadObject(containerId+urlWithoutExt+transformation, new ByteArrayInputStream(bytes));
                        redisCacheStoreService.saveImageObjectId(containerId+urlWithoutExt+transformation, containerId+urlWithoutExt+transformation);
                        log.info("Web "+instanceId+": Saving transformation for faster retrieval next time");
                        imageMetadataVO.setObjectId(containerId+urlWithoutExt+transformation);
                    } catch (Exception e) {
                        log.error("Unable to save transformed image: ",e);
                        imageMetadataVO.setObjectId(null);
                    }
                }
            }

            imageMetadataVO.setDownloadSize(inputStream.available());
            log.info("Web "+instanceId+": Sending service usage statistics for this request to worker servers via queue");
            rabbitMQSenderService.sendDownloadInfo(imageMetadataVO);
            return inputStream;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            ErrorVO errorVO = new ErrorVO(messageSource.getMessage("server.error",null,null));
            List<ErrorVO> errorVOs = new ArrayList<>(1);
            errorVOs.add(errorVO);
            transformationVO.errorVOs = errorVOs;
            return null;
        }
    }
}
