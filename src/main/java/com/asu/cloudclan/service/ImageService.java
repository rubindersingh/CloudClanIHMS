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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

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
            boolean noTransformation = false;
            if(transformation.length() == 0) {
                transformation = "";
                noTransformation = true;
            }

            //Look up for container+URL+transformation in redis first else proceed to next lines
            // If not found, first validate transformation
            String objectId = redisCacheStoreService.lookupImageObjectId(containerId+urlWithoutExt+transformation);
            boolean doTransformation = false;
            if(objectId == null) {
                if(!transformationVO.validateAndConvert()) {
                    ErrorVO errorVO = new ErrorVO(messageSource.getMessage("invalid.image.op.params",null,null));
                    errorVOs.add(errorVO);
                    transformationVO.errorVOs = errorVOs;
                    return null;
                }
                Image image = imageCoreService.get(containerId, urlWithoutExt);
                if(image == null) {
                    ErrorVO errorVO = new ErrorVO(messageSource.getMessage("invalid.image.url",null,null));
                    errorVOs.add(errorVO);
                    transformationVO.errorVOs = errorVOs;
                    return null;
                }
                Map<String, String> metadataMap = image.getMetadataMap();

                if(noTransformation || !metadataMap.containsKey(transformation)) {
                    if(metadataMap.containsKey("OPTIMIZED")) {
                        objectId = metadataMap.get("OPTIMIZED");
                    } else {
                        objectId = metadataMap.get("ORIGINAL");
                    }
                    doTransformation = true;
                } else {
                    objectId = metadataMap.get(transformation);
                    redisCacheStoreService.saveImageObjectId(containerId+urlWithoutExt+transformation, objectId);
                }
            }

            InputStream inputStream = swiftStorageService.downloadObject(objectId);
            ImageMetadataVO imageMetadataVO = new ImageMetadataVO();
            imageMetadataVO.setContainerId(containerId);
            imageMetadataVO.setUrl(urlWithoutExt);
            imageMetadataVO.setDownloadSize(inputStream.available());
            if(!noTransformation && doTransformation) {
                //Run required transform here and return
                inputStream = coreTransformationService.transform(inputStream, transformationVO);

                imageMetadataVO.setTransformation(transformation);
                imageMetadataVO.setTransformed(1);
                if(state.equals("p")) {
                    imageMetadataVO.setStoredSize(inputStream.available());
                    imageMetadataVO.setObjectId(containerId+urlWithoutExt+transformation);
                    imageMetadataVO.setType(ImageFormat.JPG.name());
                    swiftStorageService.uploadObject(containerId+urlWithoutExt+transformation, inputStream);
                    redisCacheStoreService.saveImageObjectId(containerId+urlWithoutExt+transformation, containerId+urlWithoutExt+transformation);
                }
            }
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
