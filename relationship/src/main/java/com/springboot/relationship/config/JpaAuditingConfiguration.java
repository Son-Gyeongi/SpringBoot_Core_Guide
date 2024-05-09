package com.springboot.relationship.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * main() 메서드가 있는 AdvancedJpaApplication 클래스에서 사용하면
 * @WebMvcTest 테스트를 수행하는 코드를 작성하면 애플리케이션 클래스를 호출하는 과정에서 예외 발생
 *
 * 그래서 아래와 같이 별도의 Configuration 클래스 생성해서 애플리케이션 클래스의 기능과 분리해서 활성화
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
