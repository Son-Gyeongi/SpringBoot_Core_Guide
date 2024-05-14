package com.springboot.valid_exception.config.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> { // 어떤 어노테이션 인터페이스인지 타입 지정
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 직접 유효성 검사 로직 작성
        if (value == null) { // null에 대한 허용 여부 로직 추가
            return false; // false가 리턴되면 MethodArgumentNotValidException 예외 발생
        }

        // 지정한 정규식과 비교해서 알맞은 형식을 띠고 있는지 검사
        return value.matches("01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$");
    }
}
