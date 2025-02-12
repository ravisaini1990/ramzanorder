package com.ravi.ramzanorder.repo;

import com.ravi.ramzanorder.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.email = :username")
    List<Order> findOrderByUsername(@Param("username") String username);
}