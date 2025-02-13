package com.aengpyo.orderservice.repository.order;

import com.aengpyo.orderservice.domain.order.Order;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryOrderRepository implements OrderRepository{

    private static final Map<Long, Order> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Order save(Order order) {
        order.setId(++sequence);
        store.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Order> findByMemberId(Long memberId) {
        return store.values().stream()
                .filter(o -> o.getMemberId().equals(memberId))
                .toList();
    }

    @Override
    public List<Order> findByProductId(Long productId) {
        return store.values().stream()
                .filter(o -> o.getProductId().equals(productId))
                .toList();
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(store.values());
    }
}
