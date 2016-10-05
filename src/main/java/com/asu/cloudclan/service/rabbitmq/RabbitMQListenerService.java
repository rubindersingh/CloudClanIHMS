package com.asu.cloudclan.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by rubinder on 10/3/16.
 */
@Service
public class RabbitMQListenerService {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "workerQueue", durable = "true"),
    exchange = @Exchange(value = "worker", ignoreDeclarationExceptions = "true"),
    key = "cloudclan"))
    public void receive(String message) {
        System.out.println("Hey I Got it "+message);
    }
}
