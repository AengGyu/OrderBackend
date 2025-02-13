package com.aengpyo.orderservice.service.product;

import com.aengpyo.orderservice.domain.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    Product register(Product product);
    Optional<Product> findProductById(Long id);
    Optional<Product> findProductByName(String name);
    List<Product> findAllProducts();
    Product updateProduct(Product product);
}