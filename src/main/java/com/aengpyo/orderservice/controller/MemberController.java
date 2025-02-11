package com.aengpyo.orderservice.controller;

import com.aengpyo.orderservice.SessionConst;
import com.aengpyo.orderservice.domain.Grade;
import com.aengpyo.orderservice.domain.member.Member;
import com.aengpyo.orderservice.dto.member.MemberRegisterRequest;
import com.aengpyo.orderservice.dto.member.MemberResponse;
import com.aengpyo.orderservice.exception.CommonException;
import com.aengpyo.orderservice.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register") //회원가입
    public ResponseEntity<MemberResponse> register(@Validated @RequestBody MemberRegisterRequest registerRequest,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new CommonException("유효성 검사 실패", HttpStatus.BAD_REQUEST);
        }

        if (memberService.findMemberByLoginId(registerRequest.getLoginId()).isPresent()) {
            throw new CommonException("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST);
        }

        Member member = new Member(registerRequest.getLoginId(), registerRequest.getPassword(), registerRequest.getName());
        member.setGrade(Grade.BRONZE);

        Member registeredMember = memberService.register(member);

        return ResponseEntity.ok(new MemberResponse(
                registeredMember.getId(),
                registeredMember.getLoginId(),
                registeredMember.getName(),
                registeredMember.getGrade()
        ));
    }

    @GetMapping // 전체 회원 조회
    public ResponseEntity<List<MemberResponse>> getAllMembers(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_SESSION);

        if (member.getGrade() != Grade.ADMIN) {
            throw new CommonException("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        List<Member> members = memberService.findAllMembers();

        List<MemberResponse> responseList = members.stream()
                .map(m -> new MemberResponse(
                        m.getId(),
                        m.getLoginId(),
                        m.getName(),
                        m.getGrade()
                )).toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}") //개인 정보 조회
    public ResponseEntity<MemberResponse> getMember(@PathVariable long id) {
        Member member = memberService.findMemberById(id)
                .orElseThrow(() -> new CommonException("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new MemberResponse(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getGrade()
        ));
    }
}
