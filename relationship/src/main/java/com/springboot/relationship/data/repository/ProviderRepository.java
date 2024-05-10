package com.springboot.relationship.data.repository;

import com.springboot.relationship.data.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 공급업체 엔티티를 활용할 수 있게 리포지터리 생성
 */
public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
