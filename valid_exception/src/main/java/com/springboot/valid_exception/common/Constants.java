package com.springboot.valid_exception.common;

/**
 * Constants 클래스를 생성한 후 ExceptionClass를 내부에 생성
 *  - 열거형을 별도로 생성해도 무관하지만 상수 개념으로 사용하기 때문에
 *  앞으로의 확장성을 위해 Constants라는 상수들을 통합 관리하는 클래스를 생성하고 내부에 ExceptionClass를 선언
 */
public class Constants {

    /*
    커스텀 예외 클래스를 생성하기에 앞서 도메인 레벨 표현을 위한 열거형 생성
    애플리케이션에서 가지고 있는 도메인 레벨을 메시지에 표현하기 위해 ExceptionClass 열거형 타입 생성

    ExceptionClass 열거형은 커스텀 예외 클래스에서 메시지 내부에 어떤 도메인에서 문제가 발생했는지 보여주는 데 사용
     */
    public enum ExceptionClass {

        PRODUCT("Product");

        private String exceptionClass;

        // 생성자
        ExceptionClass(String exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        public String getExceptionClass() {
            return exceptionClass;
        }

        @Override
        public String toString() {
            return getExceptionClass() + " Exception. ";
        }
    }
}
