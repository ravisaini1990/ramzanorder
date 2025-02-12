package com.ravi.ramzanorder.service;

import com.ravi.ramzanorder.modal.Order;
import com.ravi.ramzanorder.modal.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    List<Order> getOrderByUserId(String email);

    Order getOrderByOrderId(int orderId);

    List<Order> getOrderByUserEmail(String email);

    Order updateOrder(Order order);

    List<Order> getOrders();

    Order placeOrder(Order order);

    Order cancelOrder(int id);

    ResponseEntity<UserDTO> getUserDetails(String email);
}
