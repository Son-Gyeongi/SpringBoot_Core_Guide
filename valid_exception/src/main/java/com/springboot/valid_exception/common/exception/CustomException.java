package com.springboot.valid_exception.common.exception;

import com.springboot.valid_exception.common.Constants;
import org.springframework.http.HttpStatus;

/**
 * ExceptonClass 열거형 생성 후 커스텀 예외 클래스 생성
 */
public class CustomException extends Exception {

    /*
    커스텀 예외 클래스를 생성하는 데 필요한 내용

    1. 에러 타입(error type): HttpStatus의 reasonPhrase
    2. 에러 코드(error code): HttpStatus의 value
    3. 메시지(message): 상황별 상세 메시지
     */
    private Constants.ExceptionClass exceptionClass;
    private HttpStatus httpStatus;

    public CustomException(Constants.ExceptionClass exceptionClass, HttpStatus httpStatus, String message) {
        super(exceptionClass.toString() + message);
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
    }

    public Constants.ExceptionClass getExceptionClass() {
        return exceptionClass;
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

    public String getHttpStatusType() {
        return httpStatus.getReasonPhrase();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
