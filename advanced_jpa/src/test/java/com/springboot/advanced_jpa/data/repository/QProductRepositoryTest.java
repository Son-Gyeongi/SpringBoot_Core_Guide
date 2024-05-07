package com.springboot.advanced_jpa.data.repository;

import com.querydsl.core.types.Predicate;
import com.springboot.advanced_jpa.data.entity.Product;
import com.springboot.advanced_jpa.data.entity.QProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QProductRepositoryTest 실습 코드
 */
@SpringBootTest
class QProductRepositoryTest {

    @Autowired
    QProductRepository qProductRepository;

    // Predicate를 활용한 findOne()메서드 호출
    @Test
    public void queryDSLTest1() {
        // Predicate는 간단하게 표현식으로 정의하는 쿼리로 생각하면 된다.
        Predicate predicate = QProduct.product.name.containsIgnoreCase("펜")
                .and(QProduct.product.price.between(1000, 2500));

        Optional<Product> foundProduct = qProductRepository.findOne(predicate);

        if (foundProduct.isPresent()) {
            Product product = foundProduct.get();
            System.out.println(product.getNumber());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getStock());
        }
    }

    /*
    queryDSLTest1는 Predicate를 명시적으로 정의하고 사용했지만 queryDSLTest2는 서술부만 가져다 사용할 수 있다.
     */
    @Test
    public void queryDSLTest2() {
        QProduct qProduct = QProduct.product;

        Iterable<Product> productList = qProductRepository.findAll(
                qProduct.name.contains("펜")
                        .and(qProduct.price.between(550, 1500))
        );

        for (Product product : productList) {
            System.out.println(product.getNumber());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getStock());
        }
    }
}