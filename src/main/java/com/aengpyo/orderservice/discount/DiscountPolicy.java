package com.aengpyo.orderservice.discount;

import com.aengpyo.orderservice.domain.Grade;

public interface DiscountPolicy {

    int discount(Grade grade, int price);
}
