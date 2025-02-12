package com.ravi.ramzanorder.service;

import com.ravi.ramzanorder.modal.Order;
import com.ravi.ramzanorder.repo.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderJpaRepository orderRepo;

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
        Order order1 = orderRepo.findById(order.getId()).map(existingOrder -> {
                    existingOrder.setOrderDate(order.getOrderDate());
                    existingOrder.setPrice(order.getPrice());
                    existingOrder.setStatus(order.getStatus());
                    return existingOrder;
                }
                ).orElse(null);

        assert order1 != null;
        return orderRepo.save(order1);
    }

    @Override
    public Order placeOrder(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepo.findAll();
    }
}
