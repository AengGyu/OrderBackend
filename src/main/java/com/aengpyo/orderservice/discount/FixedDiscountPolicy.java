package com.aengpyo.orderservice.discount;

import com.aengpyo.orderservice.domain.Grade;
import org.springframework.stereotype.Component;

//@Component
public class FixedDiscountPolicy implements DiscountPolicy {

    @Override
    public int discount(Grade grade, int price) {
        if (grade == Grade.GOLD) {
            return 2000;
        } else if (grade == Grade.SILVER) {
            return 1000;
        } else {
            return 0;
        }
    }
}
