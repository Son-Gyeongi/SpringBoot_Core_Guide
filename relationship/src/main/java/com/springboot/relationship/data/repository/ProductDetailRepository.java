package com.springboot.relationship.data.repository;

import com.springboot.relationship.data.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 생성된 상품정보 엔티티(ProductDetail) 객체들을 사용하기 위해 리포지토리 인터페이스 생성
 */
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
