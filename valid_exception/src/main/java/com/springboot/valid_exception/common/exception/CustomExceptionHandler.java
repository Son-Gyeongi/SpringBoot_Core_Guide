package com.springboot.valid_exception.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 스프링 부트의 예외 처리 방식 - 모든 컨트롤러의 예외를 처리
 * @RestControllerAdvice를 활용한 핸들러 클래스 생성
 *
 * @RestControllerAdvice, @RControllerAdvice - 스프링에서 제공하는 어노테이션
 * @Controller, @RestController 에서 발생하는 예외를 한 곳에서 관리하고 처리할 수 있게 하는 기능을 수행
 *
 * CheckedException과 UncheckedException을 구분해서 확인하면 적절한 예외 처리에 도움이 될 것이다.
 * https://docs.oracle.com/en/java/javase/11/docs/api/overview-tree.html
 */
//@RestControllerAdvice(basePackages = "com.springboot.valid_exception") // 별도 설정을 통해 예외를 관제하는 범위를 지정할 수 있음
@RestControllerAdvice
public class CustomExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    /*
    @ExceptionHandler
    - @Controller, @RestController가 적용된 빈에서 발생하는 예외를 잡아 처리하는 메서드를 정의할 때 사용
    - 어떤 예외 클래스를 처리할 지 value 속성으로 등록, 배열 형식으로 전달 받을 수 있어 여러 예외 클래스 등록 가능

    아래의 경우 RuntimeException이 발생하면 처리하도록 코드 작성,
    RuntimeException에 포함되는 각종 예외가 발생할 경우 처리
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleException(RuntimeException e, HttpServletRequest request) {

        /*
        아래 코드의 경우 클라이언트에게 오류가 발생했다는 것을 알리는 응답 메시지를 구성해서 리턴
         */

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("Advice 내 handleException 호출, {}, {}", request.getRequestURI(), e.getMessage());

        Map<String, String> map = new HashMap<>(); // Map 객체에 응답할 메시지 구성
        map.put("error type", httpStatus.getReasonPhrase()); // "Bad Request"
        map.put("code", "400"); // "400"
        map.put("message", e.getMessage()); // "getRuntimeException 메서드 호출"

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
