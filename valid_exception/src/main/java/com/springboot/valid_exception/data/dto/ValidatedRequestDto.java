package com.springboot.valid_exception.data.dto;

import com.springboot.valid_exception.data.group.ValidationGroup1;
import com.springboot.valid_exception.data.group.ValidationGroup2;
import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ValidatedRequestDto {
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
    // 검증 그룹 설정은 DTO 객체에서 함
    @Min(value = 20, groups = ValidationGroup1.class) // 어느 그룹에 맞춰 유효성 검사를 실시할 것인지 지정
    @Max(value = 40, groups = ValidationGroup1.class)
    int age;

    // 문자열 길이 검증
    @Size(min = 0, max = 40) // 0이상 40이하의 범위를 허용
    String description;

    // 값의 범위 검증
    @Positive(groups = ValidationGroup2.class)
    int count;
    
    // Boolean 검증
    @AssertTrue // true인지 체크, null값은 체크하지 않음
    boolean booleanCheck;
}
