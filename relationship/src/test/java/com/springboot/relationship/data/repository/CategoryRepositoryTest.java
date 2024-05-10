package com.springboot.relationship.data.repository;

import com.springboot.relationship.data.entity.Category;
import com.springboot.relationship.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void relationship() {
        // 테스트 데이터 생성
        Product product = new Product();
        product.setName("펜");
        product.setPrice(2000);
        product.setStock(100);

        productRepository.save(product);

        Category category = new Category();
        category.setCode("S1");
        category.setName("도서");
        category.getProducts().add(product); // Product 객체를 Category 에서 생성한 리스트 객체에 추가해서 연관관계 설정

        categoryRepository.save(category);

        // 테스트 코드
        List<Product> products = categoryRepository.findById(1L).get().getProducts();

        for (Product foundProduct : products) {
            System.out.println(foundProduct);
        }
    }
}