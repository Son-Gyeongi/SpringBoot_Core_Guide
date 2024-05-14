package com.springboot.valid_exception.data.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ValidRequestDto {
    /**
     * 유효성 검사를 위한 조건을 설정
     * 실습에 사용한 스프링 부트 2.5.6버전은 Hibernate Validater 6.2.0Final 버전 사용
     * - 해당 버전은 Jakarta Bean Validation 2.0의 스펙을 따르며 아래 공식문서를 통해 확인 가능
     * https://beanvalidation.org/2.0-jsr380/spec/#builtinconstraints-decimalmax
     */

    // 문자열 검증
    @NotBlank // null, "", " " 을 허용하지 않음
    String name;

    // 이메일 검증
    @Email // 이메일 형식을 검사, ""는 허용됨
    String email;

    // 정규식 검증
    @Pattern(regexp = "01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    String phoneNumber;

    // 최댓값/최솟값 검증
    @Min(value = 20) @Max(value = 40) // 20이상, 40이하의 값을 허용
    int age;

    // 문자열 길이 검증
    @Size(min = 0, max = 40) // 0이상 40이하의 범위를 허용
    String description;

    // 값의 범위 검증
    @Positive // 양수를 허용
    int count;
    
    // Boolean 검증
    @AssertTrue // true인지 체크, null값은 체크하지 않음
    boolean booleanCheck;
}
