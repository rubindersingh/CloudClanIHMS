package com.asu.cloudclan.controller;

import com.asu.cloudclan.service.UploadService;
import com.asu.cloudclan.vo.ImageVO;
import com.asu.cloudclan.vo.UploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by rubinder on 10/25/16.
 */
@RestController
public class UploadController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${instanceId}")
    private int instanceId;

    @Autowired
    UploadService uploadService;

    @RequestMapping(value="/images/", method = RequestMethod.POST, produces = {"application/json"})
    public UploadVO upload(UploadVO uploadVO) {
        log.info("Web "+instanceId+": Request for upload images");
        uploadService.upload(uploadVO);
        return uploadVO;
    }
}

