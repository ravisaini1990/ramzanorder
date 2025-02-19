package com.ravi.ramzanorder.service;

import com.ravi.ramzanorder.modal.Order;
import com.ravi.ramzanorder.modal.OrderStatus;
import com.ravi.ramzanorder.modal.UserDTO;
import com.ravi.ramzanorder.rabbitmq.producer.RabbitMQUndeliveredProducer;
import com.ravi.ramzanorder.repo.OrderJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderJpaRepository orderRepo;

    @Autowired
    private RestTemplate restTemplate;


    private final RabbitMQUndeliveredProducer unDeliveredProducer;

    // Inject RabbitMqUnDeliveredProducer via constructor
    public OrderServiceImpl(RabbitMQUndeliveredProducer unDeliveredProducer) {
        this.unDeliveredProducer = unDeliveredProducer;
    }

    @Override
    public Order getOrderByOrderId(int orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> getOrderByUserId(String email) {
        return orderRepo.findOrderByUsername(email);
    }

    @Override
    public List<Order> getOrderByUserEmail(String email) {
        return orderRepo.findOrderByUsername(email);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepo.findById(order.getId()).map(existingOrder -> {
            existingOrder.setOrderDate(order.getOrderDate());
            existingOrder.setPrice(order.getPrice());
            existingOrder.setProductId(order.getProductId());
            existingOrder.setStatus(order.getStatus());
            return orderRepo.save(existingOrder);
        }).orElse(null);
    }

    @Override
    public Order placeOrder(Order order) {
       Order newOrder =  orderRepo.save(order);
        try {
            unDeliveredProducer.sendMessage(newOrder);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return newOrder;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Order cancelOrder(int id) {
        Order cancelledOrder = orderRepo.findById(id).orElse(null);
        if (cancelledOrder != null) {
            cancelledOrder.setStatus(OrderStatus.Cancelled);
        }
        return cancelledOrder;
    }

    @Override
    public ResponseEntity<UserDTO> getUserDetails(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
        }
        //String url = "http://localhost:8081/user/" + email;
        //or with eurekaServer registory
        String url = "http://RAMZANAUTHSERVICE/user/" + email;
        try {
            ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
            /// to get value from product micro services - for testing
            // String url1 = "http://localhost:8080/product/" + "1";
            //ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, null, String.class);
            //logger.info("Resonse of product <<<<{}", response1);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), "Failed to fetch user: " + e.getResponseBodyAsString());
        }
    }
}
