package com.springboot.advanced_jpa.data.repository;

import com.springboot.advanced_jpa.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

/**
 * 쿼리 메서드 이름에 키워드 넣지 않고
 * Sort 객체를 활용해 매개변수로 받아들인 정렬 기준을 가지고 쿼리문 작성
 */
@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void sortingAndPagingTest() {
        Product product1 = new Product();
        product1.setName("펜");
        product1.setPrice(1000);
        product1.setStock(100);
        product1.setCreatedAt(LocalDateTime.now());
        product1.setUpdatedAt(LocalDateTime.now());

        Product product2 = new Product();
        product2.setName("펜");
        product2.setPrice(5000);
        product2.setStock(300);
        product2.setCreatedAt(LocalDateTime.now());
        product2.setUpdatedAt(LocalDateTime.now());

        Product product3 = new Product();
        product3.setName("펜");
        product3.setPrice(500);
        product3.setStock(50);
        product3.setCreatedAt(LocalDateTime.now());
        product3.setUpdatedAt(LocalDateTime.now());

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        Product savedProduct3 = productRepository.save(product3);

        // 정렬 처리
        // 쿼리 메서드에 Sort 객체 전달
//        productRepository.findByName("펜", Sort.by(Sort.Order.asc("price")));
        System.out.println("Sort 객체를 활용한 테스트 = " + productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"))));
//        productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"), Sort.Order.desc("stock")));
        System.out.println("Sort 객체를 활용한 테스트2 = " + productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"), Sort.Order.desc("stock"))));
        System.out.println("Sort 부분을 하나의 메서드로 분리 = " + productRepository.findByName("펜", getSort()));

        // 페이징 처리
        // 페이징 쿼리 메서드를 호출하는 방법
        // Page 객체를 그대로 출력하면 해당 객체의 값을 보여주지 않고 몇 번째 페이지에 해당하는지만 확인
        Page<Product> productPage = productRepository.findByName("펜", PageRequest.of(0, 2));
        System.out.println("productPage = " + productPage);
        // 각 페이지를 구성하는 세부적인 값을 보려면 아래와 같이 - getContent() 메서드를 사용해 출력하면 배열 형태로 값이 출력됨
        System.out.println("productPage 세부적인 값 = " + productPage.getContent());
    }

    // Sort 부분을 하나의 메서드로 분리해서 쿼리 메서드를 호출하는 코드를 작성하는 방법
    private Sort getSort() {
        return Sort.by(
                Sort.Order.asc("price"),
                Sort.Order.desc("stock")
        );
    }
}