package com.springboot.relationship.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * BaseEntity는 코드의 중복을 없애기 위해서 각 엔티티에 공통으로 들어가게 되는 칼럼(필드)을 하나의 클래스에 모은 것이다.
 */
@Getter
@Setter
@ToString
@MappedSuperclass // JPA의 엔티티 클래스가 상속받을 경우 자식 클래스에게 매핑 정보를 전달
@EntityListeners(AuditingEntityListener.class) // 엔티티를 데이터베이스에 적용하기 전후로 콜백을 요청할 수 있게 하는 어노테이션
// AuditingEntityListener는 엔티티의 Auditing 정보를 주입하는 JPA 엔티티 리스너 클래스
public class BaseEntity {

    @CreatedDate // 데이터 생성 날짜를 자동으로 주입하는 어노테이션
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 데이터 수정 날짜를 자동으로 주입하는 어노테이션
    private LocalDateTime updatedAt;
}
