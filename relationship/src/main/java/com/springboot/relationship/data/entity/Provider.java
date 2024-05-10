package com.springboot.relationship.data.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 공급업체 테이블에 매핑되는 엔티티 클래스(공급업체는 Provider라는 도메인을 사용해서 정의)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "provider")
public class Provider extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
