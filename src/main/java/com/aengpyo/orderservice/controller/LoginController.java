package com.aengpyo.orderservice.controller;

import com.aengpyo.orderservice.SessionConst;
import com.aengpyo.orderservice.domain.member.Member;
import com.aengpyo.orderservice.dto.member.MemberLoginRequest;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@Validated @RequestBody MemberLoginRequest loginRequest,
                                                BindingResult bindingResult,
                                                HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new CommonException("유효성 검사 실패", HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findMemberByLoginId(loginRequest.getLoginId())
                .filter(m -> m.getPassword().equals(loginRequest.getPassword()))
                .orElseThrow(() -> new CommonException("아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST));

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_SESSION, member);

        return ResponseEntity.ok(new MemberResponse(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getGrade()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }
}
