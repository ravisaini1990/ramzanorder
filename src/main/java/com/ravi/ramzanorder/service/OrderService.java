package com.ravi.ramzanorder.service;

import com.ravi.ramzanorder.modal.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrderByUserId(String email);

    Order getOrderByOrderId(int orderId);

    List<Order> getOrderByUserEmail(String email);

    Order updateOrder(Order order);

    List<Order> getOrders();

    Order placeOrder(Order order);
}
