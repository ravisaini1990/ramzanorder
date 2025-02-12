package com.ravi.ramzanorder.rabbitmq.consumer;


import com.ravi.ramzanorder.config.queue.RabbitMQConfig;
import com.ravi.ramzanorder.modal.Order;
import com.ravi.ramzanorder.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQDeliveredOrderConsumer {

    @Autowired
    OrderService orderService;

    @RabbitListener(queues = RabbitMQConfig.DELIVERED_QUEUE_NAME, messageConverter = "converter")
    public void receiveMessage(Order order) {
        Order deliveredOrder = orderService.updateOrder(order);
        System.out.println("Order status is updated successfully..! " + deliveredOrder);
    }
}