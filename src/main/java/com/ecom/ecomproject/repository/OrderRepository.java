package com.ecom.ecomproject.repository;

import com.ecom.ecomproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}