package com.springboot.relationship.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true) // callSuper는 부모 클래스의 필드를 포함하는 역할
@EqualsAndHashCode(callSuper = true) // callSuper는 부모 클래스의 필드를 포함하는 역할
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    // 일대일 양방향 매핑을 위한 Product 엔티티에 코드 추가
    // 양방향으로 매핑해도 좋아, 하지만 외래키를 가지는 주인은 한 테이블만 하자.
    // 여기서 product는 연관관계를 갖고 있는 상태 엔티티에 있는 연관관계 필드의 이름임
    // mappedBy는 어떤 객체가 주인인지 표시하는 속성, ProductDetail 엔티티가 Product 엔티티의 주인이 되는 것
    @OneToOne(mappedBy = "product") // mappedBy를 사용해서 product 테이블에는 외래키가 생기지 않게 함
    @ToString.Exclude // 양방향 설정이 필요할 경우에 순환참조 제거를 위해 exclude를 사용해 ToString에서 제외 설정함
    // 테이블간 양방향으로 연관관계가 설정되면 ToString을 사용할 때 순환참조 발생, 단방향으로 바꾸거나, 양방향이 필요할 경우 ToString.Exclude사용
    private ProductDetail productDetail;

    // 상품 엔티티와 공급업체 엔티티의 다대일 연관관계 설정
    // - 일반적으로 외래키를 갖는 쪽이 주인의 역할을 수행하기 때문에, 상품 엔티티가 공급업체 엔티티의 주인이다.
    // Product 엔티티가 주인이므로 ProductRepository를 활용해서 테스트를 진행
    @ManyToOne
    @JoinColumn(name = "provider_id")
    @ToString.Exclude
    private Provider provider;
}
