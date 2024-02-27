package com.practice.praticando.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.praticando.entities.OrderItem;
import com.practice.praticando.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}
