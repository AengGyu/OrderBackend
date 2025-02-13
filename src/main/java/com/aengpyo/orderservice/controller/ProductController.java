package com.aengpyo.orderservice.controller;

import com.aengpyo.orderservice.SessionConst;
import com.aengpyo.orderservice.domain.Grade;
import com.aengpyo.orderservice.domain.member.Member;
import com.aengpyo.orderservice.domain.product.Product;
import com.aengpyo.orderservice.dto.product.ProductRequest;
import com.aengpyo.orderservice.dto.product.ProductResponse;
import com.aengpyo.orderservice.exception.CommonException;
import com.aengpyo.orderservice.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> register(@Validated @RequestBody ProductRequest productRequest,
                                                    BindingResult bindingResult,
                                                    HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new CommonException("유효성 검사 실패", HttpStatus.BAD_REQUEST);
        }

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_SESSION);

        if (member.getGrade() != Grade.ADMIN) {
            throw new CommonException("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        if (productService.findProductByName(productRequest.getName()).isPresent()) {
            throw new CommonException("이미 존재하는 상품입니다.", HttpStatus.BAD_REQUEST);
        }


        Product product = new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getQuantity());
        Product registered = productService.register(product);

        return ResponseEntity.ok(new ProductResponse(
                registered.getId(),
                registered.getName(),
                registered.getPrice(),
                registered.getQuantity()
        ));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id) {
        Product product = productService.findProductById(id)
                .orElseThrow(() -> new CommonException("존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
        ));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<Product> products = productService.findAllProducts();

        List<ProductResponse> responseList = products.stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getQuantity()
                )).toList();

        return ResponseEntity.ok(responseList);
    }
}