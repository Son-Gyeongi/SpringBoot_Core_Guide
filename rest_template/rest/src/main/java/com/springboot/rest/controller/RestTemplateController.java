package com.springboot.rest.controller;

import com.springboot.rest.dto.MemberDto;
import com.springboot.rest.service.RestTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 서비스 코드를 연결하는 컨트롤러 코드
 */
@RestController
@RequestMapping("/rest-template")
public class RestTemplateController {

    private final RestTemplateService restTemplateService;

    public RestTemplateController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping
    public String getName() {
        return restTemplateService.getName();
    }

    @GetMapping("/path-variable")
    public String getNameWithPathVariable() {
        return restTemplateService.getNameWithPathVariable();
    }

    @GetMapping("/parameter")
    public String getNameWithParameter() {
        return restTemplateService.getNameWithParameter();
    }

    @PostMapping
    public ResponseEntity<MemberDto> postDto() {
        return restTemplateService.postWithParamAndBody();
    }

    @PostMapping("/header")
    public ResponseEntity<MemberDto> postWithHeader() {
        return restTemplateService.postWithHeader();
    }
}
