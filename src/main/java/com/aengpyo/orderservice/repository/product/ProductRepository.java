package com.aengpyo.orderservice.repository.product;

import com.aengpyo.orderservice.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    List<Product> findAll();
}