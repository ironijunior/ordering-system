package com.ironijunior.orderingsystem.service;

import com.ironijunior.orderingsystem.domain.Order;
import com.ironijunior.orderingsystem.domain.OrderItem;
import com.ironijunior.orderingsystem.domain.Product;

import java.util.Set;

public interface OrderService {

    Order create(Set<OrderItem> orderItems);

    Order get(Long id);

}
