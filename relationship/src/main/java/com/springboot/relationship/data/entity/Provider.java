package com.springboot.relationship.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // 다대일, 일대다 양방향 매핑
//    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER) // mappedBy 외래키 생성 안함
    // 영속성 전이 타입 설정, PERSIST : 엔티티가 영속화할 때 연관된 엔티티도 함께 영속화
    @OneToMany(mappedBy = "provider", cascade = CascadeType.PERSIST, orphanRemoval = true) // 고아 객체를 제거하는 기능 추가
    @ToString.Exclude
    private List<Product> productList = new ArrayList<>();
}
