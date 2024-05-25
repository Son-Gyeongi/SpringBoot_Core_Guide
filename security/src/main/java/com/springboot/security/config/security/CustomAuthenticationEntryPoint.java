package com.springboot.security.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.security.data.dto.EntryPointErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SecurityConfiguration 클래스에서 인증과 인가의 과정의 예외 상황
 * - 인증 과정에서 예외가 발생할 경우 예외를 전달한다.
 *
 * CustomAuthenticationEntryPoint
 * - 인증이 실패한 상활을 처리한다.
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    /*
    CustomAccessDeniedHandler 와 다르게 예외 처리를 위해 리다이렉트가 아니라
    직접 Response를 생성해서 클라이언트에게 응답하는 방식으로 구현했다.
    - 컨트롤러에서는 응답을 위한 설정들이 자동으로 구현되기 때문에 별도의 작업이 필요하지 않았지만
        여기서는 응답값을 설정할 필요가 있다.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("[commence] 인증 실패로 response.sendError 발생");

        // 메시지를 담기 위해 EntryPointErrorResponse 객체를 사용해 메시지를 설정
        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();
        entryPointErrorResponse.setMsg("인증이 실패하였습니다.");

        response.setStatus(401); // 상태 코드 설정
        response.setContentType("application/json"); // 콘텐츠 타입 설정
        response.setCharacterEncoding("utf-8");
        // ObjectMapper 를 사용해 EntryPointErrorResponse 객체를 바디 값으로 파싱한다.
        response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
    }

    // 굳이 메시지를 설정할 필요가 없다면 commence() 메서드 내부에
    // 아래와 같이 한 줄만 작성하는 식으로 인증 실패 코드만 전달할 수 있다.
    /*@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401 에러 코드
    }*/

    /**
     * 전체적인 코드 구조가 CustomAccessDeniedHandler와 동일하기 때문에
     * 지금까지 소개한 세가지 응답을 구성하는 방식을 각 메서드에 혼용할 수 있다.
     *
     * 1. response.sendRedirect() 로 전달
     * 2. response에 메시지로 전달
     * 3. response.sendError() 로 전달
     */
}
