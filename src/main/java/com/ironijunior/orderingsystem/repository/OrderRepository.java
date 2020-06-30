package com.ironijunior.orderingsystem.repository;

import com.ironijunior.orderingsystem.domain.Order;
import com.ironijunior.orderingsystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
