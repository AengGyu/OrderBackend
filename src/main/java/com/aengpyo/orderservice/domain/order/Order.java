package com.aengpyo.orderservice.domain.order;

import com.aengpyo.orderservice.domain.Grade;
import com.aengpyo.orderservice.domain.member.Member;
import com.aengpyo.orderservice.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    // 주문자 정보
    private Long memberId; // 회원 고유 id
    private String memberName;
    private Grade grade;


    // 상품 정보
    private Long productId; // 상품 고유 id
    private String productName;
    private int price;
    private int quantity;

    private int totalPrice;

    public Order(Long memberId, String memberName, Grade grade, Long productId, String productName, int price, int quantity, int totalPrice) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.grade = grade;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
