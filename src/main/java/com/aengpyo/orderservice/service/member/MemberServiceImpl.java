package com.aengpyo.orderservice.service.member;

import com.aengpyo.orderservice.domain.member.Member;
import com.aengpyo.orderservice.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member register(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findMemberById(Long id) {
        return  memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }
}
