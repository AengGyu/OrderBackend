package com.aengpyo.orderservice.domain.member;

import com.aengpyo.orderservice.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long id; // 고유 식별자
    private String loginId;
    private String password;
    private String name;
    private Grade grade;

    public Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
}
