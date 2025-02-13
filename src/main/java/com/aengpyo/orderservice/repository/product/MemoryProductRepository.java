package com.aengpyo.orderservice.repository.product;

import com.aengpyo.orderservice.domain.product.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryProductRepository implements ProductRepository{

    private static final Map<Long, Product> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Product save(Product product) {
        product.setId(++sequence);
        store.put(product.getId(), product);

        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return store.values().stream()
                .filter(p -> p.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Product update(Product product) {
        if (store.containsKey(product.getId())) {
            store.put(product.getId(), product);
        }

        return product;
    }
}