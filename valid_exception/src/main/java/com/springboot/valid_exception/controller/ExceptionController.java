package com.springboot.valid_exception.controller;

import com.springboot.valid_exception.common.Constants;
import com.springboot.valid_exception.common.exception.CustomException;
import com.springboot.valid_exception.common.exception.CustomExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomExceptionHandler를 테스트하기 위해 예외를 발생시킬 수 있는 컨트롤러 생성
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @GetMapping
    public void getRuntimeException() {
        throw new RuntimeException("getRuntimeException 메서드 호출");
    }

    /*
    스프링 부트의 예외 처리 방식 - 특정 컨트롤러의 예외를 처리
     컨트롤러 클래스 내 handleException() 어노테이션을 사용한 메서드를 선언하면
     해당 클래스에 국한해서 예외 처리를 할 수 있다.

     @ControllerAdvice 의 글로벌 예외 처리와 @Controller 내의 컨트롤러 예외 처리에 동일한 타입의 예외 처리를 하게 되면
     범위가 좁은 컨트롤러의 핸들러 메서드가 우선순위를 가지게 된다.
     - com.springboot.valid_exception.controller.ExceptionController 클래스 내 handleException 호출, /exception, getRuntimeException 메서드 호출
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleException(RuntimeException e, HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("클래스 내 handleException 호출, {}, {}", request.getRequestURI(), e.getMessage());

        HashMap<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }


    /*
    CustomException을 발생시키는 컨트롤러 메서드
     */
    @GetMapping("/custom")
    public void getCustomException() throws CustomException {
        throw new CustomException(Constants.ExceptionClass.PRODUCT, HttpStatus.BAD_REQUEST, "getCustomException 메서드 호출");
    }
}
