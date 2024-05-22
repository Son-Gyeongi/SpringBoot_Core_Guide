package com.springboot.serverbox.controller;

import com.springboot.serverbox.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RestTemplate 테스트를 위해서 서버 용도 프로젝트 생성
 */
@RestController
@RequestMapping("/api/v1/crud-api")
public class CrudController {

    /*
    GET 형식의 요청
     */
    // 아무 파라미터가 없는 경우
    @GetMapping
    public String getName() {
        return "Flature";
    }

    // PathVariable을 사용하는 경우
    @GetMapping(value = "/{variable}")
    public String getVariable(@PathVariable String variable) {
        return variable;
    }

    // RequestParameter를 사용하는 경우
    @GetMapping("/param")
    public String getNameWithParam(@RequestParam String name) {
        return "Hello. " + name + "!";
    }


    /*
    Post 형식의 요청
     */
    // 요청 파라미터(Request Parameter)와 요청 바디(Request Body)를 함께 받도록 구현
    @PostMapping
    public ResponseEntity<MemberDto> getMember(
            @RequestBody MemberDto request,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String organization
    ) {
        System.out.println(request.getName());
        System.out.println(request.getEmail());
        System.out.println(request.getOrganization());

        MemberDto memberDto = new MemberDto();
        memberDto.setName(name);
        memberDto.setEmail(email);
        memberDto.setOrganization(organization);

        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
    }

    // 임의의 HTTP 헤더를 받도록 구현
    @PostMapping(value = "/add-header")
    public ResponseEntity<MemberDto> addHeader(@RequestHeader("my-header") String header,
                                               @RequestBody MemberDto memberDto) {
        System.out.println(header);

        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
    }
}
