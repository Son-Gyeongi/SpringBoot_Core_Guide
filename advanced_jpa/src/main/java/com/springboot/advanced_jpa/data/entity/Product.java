package com.springboot.advanced_jpa.data.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
