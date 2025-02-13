package com.aengpyo.orderservice.service.order;

import com.aengpyo.orderservice.domain.order.Order;
import com.aengpyo.orderservice.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public Order order(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findOrderByMemberId(Long memberId) {
        return orderRepository.findByMemberId(memberId);
    }

    @Override
    public List<Order> findOrderByProductId(Long productId) {
        return orderRepository.findByProductId(productId);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
