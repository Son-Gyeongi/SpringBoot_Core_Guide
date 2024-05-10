package com.springboot.relationship.data.repository;

import com.springboot.relationship.data.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 리포지토리를 생성하면 생산업체에 대한 기본적인 데이터베이스 조작이 가능해진다.
 */
public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
