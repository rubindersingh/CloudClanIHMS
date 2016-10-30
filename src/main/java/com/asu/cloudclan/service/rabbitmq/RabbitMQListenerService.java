package com.asu.cloudclan.service.rabbitmq;

import com.asu.cloudclan.service.cassandra.ImageCoreService;
import com.asu.cloudclan.vo.ImageMetadataVO;
import com.asu.cloudclan.vo.UploadVO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/3/16.
 */
@Service
public class RabbitMQListenerService {

    @Autowired
    ImageCoreService imageCoreService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "workerQueue", durable = "true"),
    exchange = @Exchange(value = "upload", ignoreDeclarationExceptions = "true"),
    key = "upload"))
    public void recieveUploadInfo(@Payload UploadVO uploadVO, Message message) {
        imageCoreService.saveUploadInfo(uploadVO);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "workerQueue", durable = "true"),
            exchange = @Exchange(value = "download", ignoreDeclarationExceptions = "true"),
            key = "download"))
    public void recieveDownloadInfo(@Payload ImageMetadataVO imageMetadataVO, Message message) {
        imageCoreService.saveDownloadInfo(imageMetadataVO);
    }
}
