package com.aengpyo.orderservice.domain.member;

import com.aengpyo.orderservice.domain.Grade;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
