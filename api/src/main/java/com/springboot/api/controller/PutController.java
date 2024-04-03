package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/put-api")
public class PutController {

    /**
     * @RequestBody와 Map을 활용한 PUT 메서드 구현
     * - 서버에 어떤 값이 들어올지 모르는 경우에는 Map 객체를 활용
     * http://localhost:8080/api/v1/put-api/member
     */
    @PutMapping(value = "/member")
    public String postMember(@RequestBody Map<String, Object> putData) {
        StringBuilder sb = new StringBuilder();

        /*putData.entrySet().forEach(map -> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });*/
        putData.forEach((key, value) -> sb.append(key).append(" : ").append(value).append("\n"));

        return sb.toString();
    }

    /**
     * DTO 객체를 활용한 PUT 메서드 구현 - 반환 값 String
     * - 서버에 들어오는 요청에 담겨 있는 값이 정해져 있는 경우 DTO 객체를 활용
     * http://localhost:8080/api/v1/put-api/member1
     */
    @PutMapping(value = "/member1")
    public String postMemberDto1(@RequestBody MemberDto memberDto) {
        return memberDto.toString();
    }

    /**
     * DTO 객체를 활용한 PUT 메서드 구현 - 반환 값 MemberDto
     * http://localhost:8080/api/v1/put-api/member2
     */
    @PutMapping(value = "/member2")
    public MemberDto postMemberDto2(@RequestBody MemberDto memberDto) {
        return memberDto;
    }

    /**
     * ResponseEntity를 활용한 PUT 메서드 구현
     */
    @PutMapping(value = "/member3")
    public ResponseEntity<MemberDto> postMemberDto3(@RequestBody MemberDto memberDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(memberDto);
    }
}
