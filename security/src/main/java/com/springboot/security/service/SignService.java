package com.springboot.security.service;

import com.springboot.security.data.dto.SignInResultDto;
import com.springboot.security.data.dto.SignUpResultDto;

/**
 * User 객체를 생성하기 위해 회원 가입을 구현하고 User 객체로 인증을 시도하는 로그인을 구현하겠다.
 *
 * 회원 가입과 로그인의 도메인은 Sign으로 통합해서 표현할 것이다.
 * 각각 Sign-up, Sign-in 으로 구분해서 기능을 구현
 */
public interface SignService {

    // 회원 가입
    SignUpResultDto signUp(String id, String password, String name, String role);

    // 로그인
    SignInResultDto signIn(String id, String password) throws RuntimeException;
}
