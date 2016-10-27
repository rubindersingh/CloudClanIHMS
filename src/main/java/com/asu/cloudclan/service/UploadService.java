package com.asu.cloudclan.service;

import com.asu.cloudclan.entity.cassandra.UserContainer;
import com.asu.cloudclan.enums.AccessType;
import com.asu.cloudclan.enums.ImageFormat;
import com.asu.cloudclan.enums.UploadStatus;
import com.asu.cloudclan.service.cassandra.ContainerCoreService;
import com.asu.cloudclan.service.core.CoreTransformationService;
import com.asu.cloudclan.service.rabbitmq.RabbitMQSenderService;
import com.asu.cloudclan.service.swift.SwiftStorageService;
import com.asu.cloudclan.vo.ErrorVO;
import com.asu.cloudclan.vo.ImageMetadataVO;
import com.asu.cloudclan.vo.ImageVO;
import com.asu.cloudclan.vo.UploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rubinder on 10/25/16.
 */
@Service
public class UploadService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    //@Autowired
    //private SwiftStorageService swiftStorageService;
    @Autowired
    private CoreTransformationService coreTransformationService;
    @Autowired
    private RabbitMQSenderService rabbitMQSenderService;
    @Autowired()
    private ContainerCoreService containerCoreService;

    public void upload(UploadVO uploadVO) {
        if(uploadVO!=null) {
            String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            UserContainer userContainer = containerCoreService.find(currentUserEmail, uploadVO.getContainerId());
            if(userContainer == null || !AccessType.hasWriteAccess(userContainer.getAccessType())) {
                List<ErrorVO> errorVOs = new ArrayList<>();
                errorVOs.add(new ErrorVO(messageSource.getMessage("insufficient.privileges", null, Locale.getDefault())));
                uploadVO.setErrorVOs(errorVOs);
                uploadVO.setFiles(null);
            } else {
                List<MultipartFile> files = uploadVO.getFiles();
                List<ImageVO> imageVOs = new ArrayList<>(files.size());
                List<ImageMetadataVO> imageMetadataVOs = new ArrayList<>(files.size());
                for (MultipartFile multipartFile : files) {
                    ImageVO imageVO = new ImageVO();
                    imageVO.setName(multipartFile.getOriginalFilename());
                    imageVO.setUrl(multipartFile.getOriginalFilename());

                    ImageMetadataVO imageMetadataVO = new ImageMetadataVO();
                    try {
                        imageMetadataVO.setUploadedSize(multipartFile.getSize());
                        String fullName = multipartFile.getOriginalFilename();
                        int dotLastIndex = fullName.lastIndexOf(".");
                        String extension = fullName.substring(dotLastIndex+1);
                        if(ImageFormat.isMimeTypeSupported(multipartFile.getContentType()) && ImageFormat.isFormatSupported(extension)) {
                            imageMetadataVO.setType(ImageFormat.getByExtension(extension).name());
                            imageMetadataVO.setTransformation("ORIGINAL");
                            InputStream inputStream = multipartFile.getInputStream();
                            if(!uploadVO.getKeepOriginal()) {
                                coreTransformationService.optimize(inputStream);
                                imageMetadataVO.setTransformation("OPTIMIZED");
                            }
                            imageMetadataVO.setStoredSize((long) inputStream.available());
                            //swiftStorageService.uploadObject(uploadVO.getContainerId()+imageVO.getUrl(), inputStream);
                            String url = fullName.substring(0,dotLastIndex);
                            imageMetadataVO.setObjectId(uploadVO.getContainerId()+url);
                            imageMetadataVO.setUrl(url);
                        } else {
                            imageVO.setStatus(UploadStatus.SKIPPED);
                            imageVO.setMessage(messageSource.getMessage("file.format.not.supported", null, null));
                        }
                    } catch (Exception e) {
                        log.error("Error on uploading file", e);
                        imageVO.setStatus(UploadStatus.FAILED);
                    }
                    imageVOs.add(imageVO);
                    imageMetadataVOs.add(imageMetadataVO);
                }
                uploadVO.setFiles(null);
                uploadVO.setImageMetadataVOs(imageMetadataVOs);
                rabbitMQSenderService.sendUploadInfo(uploadVO);
                uploadVO.setImageMetadataVOs(null);
                uploadVO.setImageVOs(imageVOs);
            }
        }
    }
}
