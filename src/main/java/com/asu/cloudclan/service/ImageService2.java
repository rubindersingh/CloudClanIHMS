package com.asu.cloudclan.service;

import com.asu.cloudclan.enums.ImageFormat;
import com.asu.cloudclan.enums.UploadStatus;
import com.asu.cloudclan.service.core.CoreTransformationService;
import com.asu.cloudclan.vo.ImageVO;
import com.asu.cloudclan.vo.UploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rubinder on 10/4/16.
 */
@Service
public class ImageService2 {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CoreTransformationService coreTransformationService;

    public List<ImageVO> upload(UploadVO uploadVO) {
        if(uploadVO!=null) {
            List<MultipartFile> files = uploadVO.getFiles();
            List<ImageVO> imageVOs = new ArrayList<>(files.size());
            for (MultipartFile multipartFile : files) {
                ImageVO imageVO = new ImageVO();
                imageVO.setName(multipartFile.getOriginalFilename());
                imageVO.setUrl(multipartFile.getName());
                if(ImageFormat.isMimeTypeSupported(multipartFile.getContentType())) {

                } else {
                    imageVO.setStatus(UploadStatus.SKIPPED);
                    imageVO.setMessage(messageSource.getMessage("file.format.not.supported", null, null));
                }
            }
        }
        return null;
    }
}
