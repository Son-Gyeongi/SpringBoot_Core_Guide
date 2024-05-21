package com.springboot.actuator.config.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.boot.actuate.info.Info.*;

/**
 * 정보 제공 인터페이스의 구현체 생성 - 액추에이터를 커스터마이징
 * InfoContributor 인터페이스의 구현체 클래스
 */
@Component
public class CustomInfoContributor implements InfoContributor {

    @Override
    public void contribute(Builder builder) {
        // Builder 객체는 액추에이터 패키지의 Info 클래스 안에 정의돼 있는 클래스로서
        // Info 엔드포인트에서 보여줄 내용을 담는 역할을 수행

        Map<String, Object> content = new HashMap<>();
        content.put("code-info", "InfoContributor 구현체에서 정의한 정보입니다");
        builder.withDetail("custom-info-contributor", content);
    }
}
