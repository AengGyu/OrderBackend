package com.aengpyo.orderservice.repository.order;

import com.aengpyo.orderservice.domain.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findByMemberId(Long memberId);
    List<Order> findByProductId(Long productId);
    List<Order> findAll();
}
