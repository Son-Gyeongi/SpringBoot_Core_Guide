package com.springboot.valid_exception.controller;

import com.springboot.valid_exception.data.dto.ValidRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 생성한 ValidRequestDto를 사용하는 컨트롤러(ValidationController) 객체를 생성
 */
@RestController
@RequestMapping("/validation")
public class ValidationController {
    private final Logger LOGGER = LoggerFactory.getLogger(ValidationController.class);

    @PostMapping("/valid")
    public ResponseEntity<String> checkValidationByValid(
            @Valid @RequestBody ValidRequestDto validRequestDto) {
        // @Valid 어노테이션 : DTO 객체에 대해 유효성 검사를 수행
        LOGGER.info(validRequestDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(validRequestDto.toString());
    }
}
