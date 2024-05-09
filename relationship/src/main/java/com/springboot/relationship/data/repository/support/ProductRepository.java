package com.springboot.relationship.data.repository.support;

import com.springboot.relationship.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 기존에 Product 엔티티 클래스와 매핑해서 사용하던 ProductRepository가 있다면
 * ProductRepositoryCustom을 상속받아 사용할 수 있다.
 *
 * 기본적으로 JpaRepository에서 제공하는 메서드도 사용할 수 있고,
 * 별도로 ProductRepositoryCustom 인터페이스에서 정의한 메서드도 구현체를 통해 사용할 수 있다.
 */
@Repository("productRepositorySupport") // ProductRepository라는 이름이 이미 사용되고 있어서 빈 생성 시 충돌을 막아주기 위해 따로 빈 이름 설정
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
