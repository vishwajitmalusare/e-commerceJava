package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.OrderItem;


public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
