package com.aengpyo.orderservice.dto.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MemberLoginRequest {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}

