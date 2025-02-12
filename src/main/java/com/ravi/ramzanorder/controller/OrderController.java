package com.ravi.ramzanorder.controller;

import com.ravi.ramzanorder.modal.Order;
import com.ravi.ramzanorder.modal.UserDTO;
import com.ravi.ramzanorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

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
        return ResponseEntity.ok().body(orderService.cancelOrder(orderId));
    }

    @GetMapping("/order/fetch_user/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return orderService.getUserDetails(email);
    }
}
