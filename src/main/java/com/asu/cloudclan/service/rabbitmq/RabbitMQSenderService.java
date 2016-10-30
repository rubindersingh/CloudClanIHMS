package com.asu.cloudclan.service.rabbitmq;

import com.asu.cloudclan.vo.ImageMetadataVO;
import com.asu.cloudclan.vo.UploadVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/3/16.
 */
@Service
public class RabbitMQSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendUploadInfo(UploadVO uploadVO) {
        rabbitTemplate.convertAndSend("upload", "upload", uploadVO);
    }

    public void sendDownloadInfo(ImageMetadataVO imageMetadataVO) {
        rabbitTemplate.convertAndSend("download", "download", imageMetadataVO);
    }

}
