package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/post-api") // 컨트롤러 클래스에 공통 URL 설정
public class PostController {

    /**
     * @RequestMapping으로 구현하기
     * http://localhost:8080/api/v1/post-api/domain
     */
    @RequestMapping(value = "/domain", method = RequestMethod.POST)
    public String postExample() {
        return "Hello Post API";
    }

    /**
     * @RequestBody와 Map을 활용한 POST API 구현
     * http://localhost:8080/api/v1/post-api/member
     */
    @PostMapping(value = "/member")
    public String postMember(@RequestBody Map<String, Object> postData) {
        StringBuilder sb = new StringBuilder();

        /*postData.entrySet().forEach(map -> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });*/
        postData.forEach((key, value) -> sb.append(key).append(" : ").append(value).append("\n"));

        return sb.toString();
    }

    /**
     * DTO 객체를 활용한 POST API 구현
     * http://localhost:8080/api/v1/post-api/member2
     */
    @PostMapping(value = "/member2")
    public String postMemberDto(@RequestBody MemberDto memberDto) {
        return memberDto.toString();
    }
}
