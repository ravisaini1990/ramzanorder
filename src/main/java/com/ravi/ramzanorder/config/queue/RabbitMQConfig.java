package com.ravi.ramzanorder.config.queue;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJackson2MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String UNDELIVERED_QUEUE_NAME = "Undelivered";
    public static final String DELIVERED_QUEUE_NAME = "Delivered";
    public static final String EXCHANGE_NAME = "Order-Exchange";
    public static final String DELIVERED_ROUTING_KEY = "d";
    public static final String UNDELIVERED_ROUTING_KEY = "un";

   // @Value("${}")

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost", 5672);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory= new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        return simpleRabbitListenerContainerFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, AbstractJackson2MessageConverter jackson2MessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2MessageConverter);
        return rabbitTemplate;
    }

    @Bean(name = "converter")
    public AbstractJackson2MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}