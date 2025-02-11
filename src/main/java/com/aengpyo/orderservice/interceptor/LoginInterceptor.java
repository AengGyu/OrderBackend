package com.aengpyo.orderservice.interceptor;

import com.aengpyo.orderservice.SessionConst;
import com.aengpyo.orderservice.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * preflight : CORS 에서 실제 요청을 보내기 전에 허용되는 http인지 request를 먼저 보낸다고 함
         * OPTION request 는 세션이 없어서 오류를 발생시킴.
         */
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;  // Preflight 요청은 통과
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_SESSION) == null) {
            throw new CommonException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        return true;
    }
}
