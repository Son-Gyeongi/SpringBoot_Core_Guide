package com.springboot.valid_exception.config.annotation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Telephone 어노테이션 인터페이스 생성
 */
@Target(ElementType.FIELD) // 해당 어노테이션이 어디서 선언할 수 있는지 정의, 필드에서 선언
@Retention(RetentionPolicy.RUNTIME) // 해당 어노테이션이 실제로 적용되고 유지되는 범위, 컴파일 이후에도 JVM에 의해 계속 참조됨, 리플렉션이나 로깅에 많이 사용되는 정책
@Constraint(validatedBy = TelephoneValidator.class) // TelephoneValidator 클래스와 매핑하는 작업을 수행
public @interface Telephone {
    // message() : 유효성 검사가 실패할 경우 반환되는 메시지를 의미
    String message() default "전화번호 형식이 일치하지 않습니다.";

    // groups() : 유효성 검사를 사용하는 그룹으로 설정
    Class[] groups() default {};

    // payload() : 사용자가 추가 정보를 위해 전달하는 값
    Class[] payload() default {};
}
