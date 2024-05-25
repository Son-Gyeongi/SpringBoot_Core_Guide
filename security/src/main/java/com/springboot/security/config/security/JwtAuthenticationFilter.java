package com.springboot.security.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAuthenticationFilter는
 * JWT 토큰으로 인증하고 SecurityContextHolder에 추가하는 필터를 설정하는 클래스
 */
/*
    OncePerRequestFilter
    - 스프링 부트에서는 필터를 여러 방법으로 구현할 수 있는데, 가장 편한 구현 방법은 필터를 상속받아 사용하는 것
    - 대표적으로 많이 사용되는 상속 객체는 GenericFilterBean과 OncePerRequestFilter 입니다.

    - 서블릿은 사용자의 요청을 받으면 서블릿을 생성해서 메모리에 저장해두고 동일한 클라이언트의 요청을 받으면 재활요하는 구조
        - GenericFilterBean을 상속받으면 RequestDispatcher에 의해 다른 서블릿으로 디스패치되면서 필터가 두 번 실행되는 현상 발생
        - OncePerRequestFilter는 GenericFilterBean을 상속받는다. 다만 매 요청마다 한 번만 실행되게끔 구현
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*
    메서드 내부 로직
    JwtTokenProvider를 통해 servletRequest에서 토큰을 추출하고, 토큰에 대한 유효성을 검사한다.
    토큰이 유효하다면 Authentication 객체를 생성해서 SecurityContextHolder에 추가하는 작업을 수행한다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // HttpServletRequest 요청 받은 곳에서 token 값 추출하기
        String token = jwtTokenProvider.resolveToken(servletRequest);
        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        /// 서블릿이 실행 되기 전

        // doFilter() 메서드는 서블릿을 실행하는 메서드
        filterChain.doFilter(servletRequest, servletResponse);

        /// 서블릿이 실행 된 후
    }
}
