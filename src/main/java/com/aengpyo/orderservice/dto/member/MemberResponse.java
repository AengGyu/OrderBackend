package com.aengpyo.orderservice.dto.member;

import com.aengpyo.orderservice.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponse {

    private Long id;
    private String loginId;
    private String name;
    private Grade grade;
}
