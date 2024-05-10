package com.springboot.relationship.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 생산 업체
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "producer")
public class Producer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    /*
    다대다 단방향 매핑 - 다대다 연관관계
    - 상품과 생산업체의 예
    - 한 종류의 상품이 여러 생산업체를 통해 생산될 수 있고, 생산업체 한 곳이 여러 상품을 생산할 수 있음
     */
    @ManyToMany
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();
    // 리스트로 필드를 가지는 객체에서는 외래키를 가지지 않기 때문에 별도의 @JoinColumn은 설정하지 않아도 됨

    // 다대다 단방향 매핑 - 연관 관계 편의 메서드
    public void addProduct(Product product) {
        products.add(product);
    }
}
