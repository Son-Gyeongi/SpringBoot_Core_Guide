package com.springboot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 패스워드는 암호화해서 저장해야 하기 때문에 PasswordEncoder를 활용해 인코딩을 수행
 *
 * PasswordEncoder는 아래와 같이 별도의 @Configuration 클래스를 생성하고 @Bean 객체로 등록하도록 구현
 * - 빈(Bean) 객체를 등록하기 위해서 생성된 클래스이기 때문에 SecurityConfiguration 클래스 같은
 * 이미 생성된 @Configuration 클래스 내부에 passwordEncoder() 메서드를 정의해도 충분하다.
 */
@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
