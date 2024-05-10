package com.springboot.relationship.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 상품 분류 엔티티 클래스
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String name;

    // 일대다 단방향 매핑
    /*
    일대다 양방향 매핑은 다루지 않을 예정
    - @OneToMany를 사용하는 입장에서는 어느 엔티티 클래스도 연관관계 주인이 될 수 없다.
    - 해당 Category에 필드를 만들면 Product 테이블에 외래키가 생성된다.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private List<Product> products = new ArrayList<>();
}
