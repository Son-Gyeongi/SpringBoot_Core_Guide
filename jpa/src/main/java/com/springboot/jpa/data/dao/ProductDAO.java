package com.springboot.jpa.data.dao;

import com.springboot.jpa.data.entity.Product;

/**
 * DAO 클래스는 일반적으로 '인터페이스-구현체' 구성으로 생성,
 * DAO 클래스는 의존성 결합을 낮추기 위한 디자인 패턴
 * - 서비스 레이어에 DAO 객체를 주입받을 때 인터페이스를 선언하는 방식으로 구성
 */
public interface ProductDAO {

    Product insertProduct(Product product);

    Product selectProduct(Long number);

    Product updateProductName(Long number, String name) throws Exception;

    void deleteProduct(Long number) throws Exception;
}
