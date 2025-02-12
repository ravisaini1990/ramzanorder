package com.ravi.ramzanorder.rabbitmq.producer;

import com.ravi.ramzanorder.config.queue.RabbitMQConfig;
import com.ravi.ramzanorder.modal.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQUndeliveredProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQUndeliveredProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Order undeliveredOrder) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.UNDELIVERED_ROUTING_KEY, undeliveredOrder);
        System.out.println("Sent message: " + undeliveredOrder);
    }


}