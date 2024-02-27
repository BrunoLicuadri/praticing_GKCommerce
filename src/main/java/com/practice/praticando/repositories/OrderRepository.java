package com.practice.praticando.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.praticando.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
