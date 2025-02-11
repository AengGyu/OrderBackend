package com.aengpyo.orderservice.dto.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
}
