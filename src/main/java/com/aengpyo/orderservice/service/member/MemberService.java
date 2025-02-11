package com.aengpyo.orderservice.service.member;

import com.aengpyo.orderservice.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member register(Member member);
    Optional<Member> findMemberById(Long id);
    Optional<Member> findMemberByLoginId(String loginId);
    List<Member> findAllMembers();
}
