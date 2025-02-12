package com.ravi.ramzanorder.controller;

import com.ravi.ramzanorder.modal.Order;
import com.ravi.ramzanorder.modal.OrderStatus;
import com.ravi.ramzanorder.modal.UserDTO;
import com.ravi.ramzanorder.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Order> getAllOrder() {
        return orderService.getOrders();
    }

    @GetMapping("/order/user/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Order> getOrderById(@PathVariable String email) {
        return orderService.getOrderByUserId(email);
    }

    @GetMapping("/order")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    List<Order> getOrdersByUserEmail(Authentication authentication) {
        return orderService.getOrderByUserEmail(authentication.getName());
    }

    @PostMapping("/order/placeOrder")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @PutMapping("/order/cancelOrder/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    ResponseEntity<Object> cancelOrder(@PathVariable int orderId) {
        Order cancelledOrder = orderService.getOrderByOrderId(orderId);
        cancelledOrder.setStatus(OrderStatus.Cancelled);
        return ResponseEntity.ok().body(orderService.updateOrder(cancelledOrder));
    }

    @GetMapping("/order/fetch_user/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
        }

        String url = "http://localhost:8081/user/" + email;

        try {
            ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
            /// to get value from product micro services - for testing
            // String url1 = "http://localhost:8080/product/" + "1";
            //ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, null, String.class);
            //logger.info("Resonse of product <<<<{}", response1);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            logger.error("Error calling user service: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), "Failed to fetch user: " + e.getResponseBodyAsString());
        }
    }
}
