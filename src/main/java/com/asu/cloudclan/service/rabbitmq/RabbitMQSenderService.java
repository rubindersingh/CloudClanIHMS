package com.asu.cloudclan.service.rabbitmq;

import com.asu.cloudclan.vo.ImageMetadataVO;
import com.asu.cloudclan.vo.UploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/3/16.
 */
@Service
public class RabbitMQSenderService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${instanceId}")
    private int instanceId;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendUploadInfo(UploadVO uploadVO) {
        log.info("Web "+instanceId+": Sending to `UPLOAD` queue");
        rabbitTemplate.convertAndSend("upload", "upload", uploadVO);
    }

    public void sendDownloadInfo(ImageMetadataVO imageMetadataVO) {
        log.info("Web "+instanceId+": Sending to `DOWNLOAD` queue");
        rabbitTemplate.convertAndSend("download", "download", imageMetadataVO);
    }

}
