package com.springboot.advanced_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing // 데이터 생성, 변경 감시하기 위해 JPA Auditing 기능 활성화 -> JpaAuditingConfiguration 클래스 생성해서 주석처리
public class AdvancedJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedJpaApplication.class, args);
    }

}
