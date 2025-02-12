package com.ravi.ramzanorder.rabbitmq.producer;

import com.ravi.ramzanorder.config.queue.RabbitMQConfig;
import com.ravi.ramzanorder.modal.Order;
import com.ravi.ramzanorder.modal.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQDeliveredProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQDeliveredProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Order deliveredOrder) {
        deliveredOrder.setStatus(OrderStatus.Delivered);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.DELIVERED_ROUTING_KEY, deliveredOrder);
        System.out.println("Sent message: " + deliveredOrder);
    }
}