package com.ravi.ramzanorder.rabbitmq.consumer;


import com.ravi.ramzanorder.config.queue.RabbitMQConfig;
import com.ravi.ramzanorder.modal.Order;
import com.ravi.ramzanorder.rabbitmq.producer.RabbitMQDeliveredProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RabbitMQUndeliveredOrderConsumer {

    @Autowired
    RabbitMQDeliveredProducer producer;

    @RabbitListener(queues = RabbitMQConfig.UNDELIVERED_QUEUE_NAME, messageConverter = "converter")
    public void receiveMessage(Order order) throws InterruptedException {
        System.out.println("Received message: " + order);
        ///  Order Processing will take time here
        Thread.sleep(10000);
        producer.sendMessage(order);
    }
}