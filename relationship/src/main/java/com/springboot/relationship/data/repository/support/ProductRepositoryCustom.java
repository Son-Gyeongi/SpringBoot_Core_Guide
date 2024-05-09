package com.springboot.relationship.data.repository.support;

import com.springboot.relationship.data.entity.Product;

import java.util.List;

/**
 * 직접 구현한 쿼리를 사용하기 위해서 JpaRepository를 상속받지 않는
 * 리포지토리 인터페이스인 ProductRepositoryCustom 생성
 * - 정의하고자 하는 기능들을 메서드로 정의
 */
public interface ProductRepositoryCustom {
    List<Product> findByName(String name);
}
