package com.aengpyo.orderservice.repository.member;

import com.aengpyo.orderservice.domain.Grade;
import com.aengpyo.orderservice.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JdbcMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member("test", "test!", "test");
        member.setGrade(Grade.BRONZE);

        Member savedMember = memberRepository.save(member);
        assertThat(savedMember).isEqualTo(member); //
    }

    @Test
    void findById() {
        Member member = new Member("test", "test!", "test");
        member.setGrade(Grade.BRONZE);
        Member savedMember = memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findById(member.getId());

        assertThat(foundMember).isPresent();
        assertThat(foundMember.get()).isEqualTo(member);
    }

    @Test
    void findByLoginId() {
        Member member = new Member("test", "test!", "test");
        member.setGrade(Grade.BRONZE);
        Member savedMember = memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByLoginId(member.getLoginId());

        assertThat(foundMember).isPresent();
        assertThat(foundMember.get()).isEqualTo(member);
    }

    @Test
    void findAll() {
        Member member1 = new Member("test", "test!", "test");
        member1.setGrade(Grade.BRONZE);
        Member savedMember1 = memberRepository.save(member1);

        Member member2 = new Member("test", "test!", "test");
        member2.setGrade(Grade.BRONZE);
        Member savedMember2 = memberRepository.save(member2);

        List<Member> allMembers = memberRepository.findAll();

        assertThat(allMembers).containsExactly(member1, member2);
    }
}