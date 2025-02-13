package com.aengpyo.orderservice.dto.order;

import com.aengpyo.orderservice.domain.Grade;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequest {

    @NotNull
    private Long productId; // 상품 고유 id

    @Min(value = 1)
    private int quantity;
}
