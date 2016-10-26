package com.asu.cloudclan.service;

import com.asu.cloudclan.enums.ImageFormat;
import com.asu.cloudclan.enums.UploadStatus;
import com.asu.cloudclan.service.core.CoreTransformationService;
import com.asu.cloudclan.service.swift.SwiftStorageService;
import com.asu.cloudclan.vo.ImageVO;
import com.asu.cloudclan.vo.UploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rubinder on 10/25/16.
 */
@Service
public class UploadService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private SwiftStorageService swiftStorageService;
    @Autowired
    private CoreTransformationService coreTransformationService;

    public List<ImageVO> upload(UploadVO uploadVO) {
        if(uploadVO!=null) {
            List<MultipartFile> files = uploadVO.getFiles();
            List<ImageVO> imageVOs = new ArrayList<>(files.size());
            for (MultipartFile multipartFile : files) {
                ImageVO imageVO = new ImageVO();
                imageVO.setName(multipartFile.getOriginalFilename());
                imageVO.setUrl(multipartFile.getOriginalFilename());
                if(ImageFormat.isMimeTypeSupported(multipartFile.getContentType())) {
                    try {
                        InputStream inputStream = multipartFile.getInputStream();
                        if(uploadVO.getKeepOriginal()) {
                            coreTransformationService.optimize(inputStream);
                        }
                        swiftStorageService.uploadObject(uploadVO.getContainerId()+imageVO.getUrl(), inputStream);
                    } catch (Exception e) {
                        log.error("Error on uploading file", e);
                        imageVO.setStatus(UploadStatus.FAILED);
                    }
                } else {
                    imageVO.setStatus(UploadStatus.SKIPPED);
                    imageVO.setMessage(messageSource.getMessage("file.format.not.supported", null, null));
                }
                imageVOs.add(imageVO);
            }
            return imageVOs;
        }
        return null;
    }
}
