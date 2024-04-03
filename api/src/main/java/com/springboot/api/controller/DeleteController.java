package com.springboot.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delete-api")
public class DeleteController {

    /**
     * @PathVariable을 활용한 DELETE 메서드 구현
     * http://localhost:8080/api/v1/delete-api/member
     */
    @DeleteMapping(value = "/{variable}")
    public String deleteVariable(@PathVariable String variable) {
        return variable;
    }

    /**
     * @RequestParam을 활용한 DELETE 메서드 구현
     */
    @DeleteMapping(value = "/request1")
    public String getRequestParam1(@RequestParam String email) {
        return "e-mail : " + email;
    }
}
