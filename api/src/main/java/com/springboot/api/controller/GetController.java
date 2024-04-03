package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    /**
     * @RequestMapping을 사용한 메서드 구현
     * http://localhost:8080/api/v1/get-api/hello
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        return "Hello World";
    }

    /**
     * 매개변수 없는 GET 메서드 구현
     * http://localhost:8080/api/v1/get-api/name
     */
    @GetMapping(value = "/name")
    public String getName() {
        return "Flature";
    }

    /**
     * @PathVariable을 활용한 GET 메서드 구현 - URL 자체에 값을 담아 요청
     * http://localhost:8080/api/v1/get-api/variable1/{String 값}
     */
    @GetMapping(value = "/variable1/{variable}")
    public String getVariable1(@PathVariable String variable) {
        return variable;
    }

    /**
     * @PathVariable에 변수명을 매핑하는 방법
     * http://localhost:8080/api/v1/get-api/variable2/{String 값}
     */
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable") String var) {
        return var;
    }

    /**
     * @RequestParam을 활용한 GET 메서드 구현 - 쿼리 형식으로 값 전달
     * http://localhost:8080/api/v1/get-api/request1?name=value1&email=value2&organization=value3
     */
    @ApiOperation(value = "GET 메서드 예제", notes = "@RequestParam을 활용한 GET Method")
    @GetMapping(value = "/request1")
    public String getRequestParam1(
            @ApiParam(value = "이름", required = true) @RequestParam String name,
            @ApiParam(value = "이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회사", required = true) @RequestParam String organization
    ) {
        return name + " " + email + " " + organization;
    }

    /**
     * @RequestParam과 Map을 조합한 GET 메서드 구현 - 쿼리 스트링에 어떤 값이 들어올지 모른다면 Map 객체 활용
     * 매개변수의 항목이 일정하지 않을 경우 Map 객체로 받는 것이 효율적
     * http://localhost:8080/api/v1/get-api/request2?key1=value1&key2=value2
     */
    @GetMapping(value = "/request2")
    public String getRequestParam2(@RequestParam Map<String, String> param) {
        StringBuilder sb = new StringBuilder(); // StringBuilder는 단일 쓰레드

        /*
        param.entrySet().forEach(map -> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });
         */
        param.forEach((key, value) -> sb.append(key).append(" : ").append(value).append("\n"));

        return sb.toString();
    }

    /**
     * DTO 객체를 활용한 GET 메서드 구현
     */
    @GetMapping(value = "/request3")
    public String getRequestParam3(MemberDto memberDto) {
//        return memberDto.getName() + " " + memberDto.getEmail() + " " + memberDto.getOrganization();
        return memberDto.toString();
    }
}