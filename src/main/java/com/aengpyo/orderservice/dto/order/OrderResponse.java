package com.aengpyo.orderservice.dto.order;

import com.aengpyo.orderservice.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String memberName;

    private String productName;
    private int price;
    private int quantity;

    private int totalPrice;
}
