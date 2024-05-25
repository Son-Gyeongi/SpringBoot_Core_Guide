package com.springboot.security.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SecurityConfiguration 클래스에서 인증과 인가의 과정의 예외 상황
 * - 권한을 확인하는 과정에서 통과하지 못하는 예외가 발생할 경우 예외를 전달한다.
 *
 * AccessDeniedHandler
 * - 액세스 권한이 없는 리소스에 접근할 경우 발생하는 예외
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        LOGGER.info("[handle] 접근이 막혔을 경우 경로 리다이렉트");
        // response에서 리다이렉트하는 sendRedirect() 메서드를 활용하는 방식으로 구현
        response.sendRedirect("/sign-api/exception");
    }
}
