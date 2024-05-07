package com.springboot.advanced_jpa.data.repository;

import com.springboot.advanced_jpa.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * QuerydslPredicateExecutor는 JpaRepository와 함께 리포지토리에서 QueryDSL을 사용할 수 있게 인터페이스 제공
 * - QuerydslPredicateExecutor를 활용하면 더욱 편하게 QueryDSL을 사용할 수 있지만
 * - join이나 fetch 기능은 사용할 수 없다는 단점이 있다.
 */
public interface QProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
    // Optional<T> findOne(Predicate predicate);
    // Predicate는 표현식을 작성할 수 있게 QueryDSL을 제공하는 인터페이스
}
