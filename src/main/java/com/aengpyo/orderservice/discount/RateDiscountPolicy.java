package com.aengpyo.orderservice.discount;

import com.aengpyo.orderservice.domain.Grade;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy {

    @Override
    public int discount(Grade grade, int price) {
        if (grade == Grade.GOLD) {
            return (int) (price * 0.2);
        } else if (grade == Grade.SILVER) {
            return (int) (price * 0.1);
        } else {
            return 0;
        }
    }
}
