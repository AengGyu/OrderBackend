package com.aengpyo.orderservice.repository.member;

import com.aengpyo.orderservice.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    List<Member> findAll();
}
