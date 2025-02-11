package com.aengpyo.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponse {

    private String errorMessage;
}
