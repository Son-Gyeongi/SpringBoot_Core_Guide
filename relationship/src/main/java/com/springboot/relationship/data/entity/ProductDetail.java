package com.springboot.relationship.data.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 상품정보에 대한 도메인
 * 하나의 상품(Product)에 하나의 상품정보(ProductDetail)만 매핑되는 일대일 관계
 */
@Entity
@Table(name = "product_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

//    @OneToOne(optional = false) // null 허용하지 않음
    @OneToOne // 다른 엔티티 객체를 필드로 정의했을 때 일대일 연관관계로 매핑하기 위해 사용, 기본은 true로 null 허용
    @JoinColumn(name = "product_number") // 상품 번호에 매핑, 매핑할 외래키 설정
    private Product product;
}
