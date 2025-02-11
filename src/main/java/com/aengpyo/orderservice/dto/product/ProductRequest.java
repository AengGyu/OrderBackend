package com.aengpyo.orderservice.dto.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter @Setter
public class ProductRequest {

    @NotEmpty
    private String name;

    @Range(min = 0, max = 1000000)
    private Integer price;

    @Range(min = 0, max = 1000)
    private Integer quantity;
}