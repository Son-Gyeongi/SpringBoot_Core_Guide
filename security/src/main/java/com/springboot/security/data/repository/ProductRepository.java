package com.springboot.security.data.repository;

import com.springboot.security.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Product 테이블과 매핑되는 Product 엔티티에 대한 인터페이스
public interface ProductRepository extends JpaRepository<Product, Long> {
}
