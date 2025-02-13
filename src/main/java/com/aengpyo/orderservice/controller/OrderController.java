package com.aengpyo.orderservice.controller;

import com.aengpyo.orderservice.SessionConst;
import com.aengpyo.orderservice.discount.DiscountPolicy;
import com.aengpyo.orderservice.domain.Grade;
import com.aengpyo.orderservice.domain.member.Member;
import com.aengpyo.orderservice.domain.order.Order;
import com.aengpyo.orderservice.domain.product.Product;
import com.aengpyo.orderservice.dto.order.OrderRequest;
import com.aengpyo.orderservice.dto.order.OrderResponse;
import com.aengpyo.orderservice.exception.CommonException;
import com.aengpyo.orderservice.service.member.MemberService;
import com.aengpyo.orderservice.service.order.OrderService;
import com.aengpyo.orderservice.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final MemberService memberService;
    private final DiscountPolicy discountPolicy;

    // 전체 주문 조회
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_SESSION);

        if (member.getGrade() != Grade.ADMIN) {
            throw new CommonException("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        List<Order> allOrders = orderService.findAllOrders();

        List<OrderResponse> responseList = allOrders.stream()
                .map(o -> new OrderResponse(
                        o.getId(),
                        o.getMemberName(),
                        o.getProductName(),
                        o.getPrice(),
                        o.getQuantity(),
                        o.getTotalPrice()
                )).toList();

        return ResponseEntity.ok(responseList);
    }

    // 주문 등록
    @PostMapping
    public ResponseEntity<OrderResponse> order(@Validated @RequestBody OrderRequest orderRequest,
                                               BindingResult bindingResult,
                                               HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new CommonException("유효성 검사 실패", HttpStatus.BAD_REQUEST);
        }

        Product product = productService.findProductById(orderRequest.getProductId())
                .orElseThrow(() -> new CommonException("존재하지 않는 상품입니다.", HttpStatus.BAD_REQUEST));

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_SESSION);

        int discount = discountPolicy.discount(member.getGrade(), product.getPrice() * orderRequest.getQuantity());

        Order order = new Order(member.getId(), member.getName(), member.getGrade(),
                product.getId(), product.getName(), product.getPrice(), orderRequest.getQuantity(),
                product.getPrice() * orderRequest.getQuantity() - discount);

        Order ordered = orderService.order(order);

        return ResponseEntity.ok(new OrderResponse(
                ordered.getId(),
                ordered.getMemberName(),
                ordered.getProductName(),
                ordered.getPrice(),
                ordered.getQuantity(),
                ordered.getTotalPrice()));
    }

    // 회원 고유 아이디로 자신의 주문 목록 조회 기능
    @GetMapping("/{memberId}")
    public ResponseEntity<List<OrderResponse>> getOrdersList(@PathVariable Long memberId) {
        memberService.findMemberById(memberId)
                .orElseThrow(() -> new CommonException("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST));

        List<Order> ordersList = orderService.findOrderByMemberId(memberId);

        List<OrderResponse> responseList = ordersList.stream()
                .map(o -> new OrderResponse(
                        o.getId(),
                        o.getMemberName(),
                        o.getProductName(),
                        o.getPrice(),
                        o.getQuantity(),
                        o.getTotalPrice()
                )).toList();

        return ResponseEntity.ok(responseList);
    }
}
