package com.aengpyo.orderservice.service.order;


import com.aengpyo.orderservice.domain.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order order(Order order);
    Optional<Order> findOrderById(Long id);
    List<Order> findOrderByMemberId(Long memberId);
    List<Order> findOrderByProductId(Long productId);
    List<Order> findAllOrders();
}
