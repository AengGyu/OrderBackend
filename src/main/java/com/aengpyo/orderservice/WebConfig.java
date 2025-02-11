package com.aengpyo.orderservice;

import com.aengpyo.orderservice.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/members/register", "/api/login", "/api/logout");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // ✅ 모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000")  // ✅ React에서 요청 가능하도록 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // ✅ 모든 HTTP 메서드 허용
                .allowedHeaders("*")  // ✅ 모든 헤더 허용
                .allowCredentials(true);  // ✅ 세션 유지 (이게 없으면 로그인 정보가 전달되지 않음)
    }

}

