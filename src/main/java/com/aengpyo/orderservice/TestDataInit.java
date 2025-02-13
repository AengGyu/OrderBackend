package com.aengpyo.orderservice;

import com.aengpyo.orderservice.domain.Grade;
import com.aengpyo.orderservice.domain.member.Member;
import com.aengpyo.orderservice.repository.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member admin = new Member();
        admin.setLoginId("admin");
        admin.setPassword("admin!");
        admin.setName("admin");
        admin.setGrade(Grade.ADMIN);    
        memberRepository.save(admin);

        Member test = new Member();
        test.setLoginId("test");
        test.setPassword("test!");
        test.setName("test");
        test.setGrade(Grade.SILVER);
        memberRepository.save(test);
    }
}
