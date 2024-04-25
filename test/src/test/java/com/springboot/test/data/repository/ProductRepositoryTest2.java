package com.springboot.test.data.repository;

import com.springboot.test.data.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*
리포지토리는 개발자가 구현하는 레이어 중에서 가장 데이터베이스와 가깝다.
리포지토리 테스트는 구현하는 목적에 대해 고민하고 작성해야 한다.
데이터베이스를 연동한 테스트는 테스트 데이터를 제거하는 코드까지 포함해서 작성하는 것이 좋다.
 */

/**
 * @SpringBootTest 어노테이션을 활용한 테스트
 * - @SpringBootTest 어노테이션을 활용하면 스프링의 모든 설정을 가져오고
 *   빈(Bean) 객체도 전체를 스캔하기 때문에 의존성 주입에 대해 고민할 필요 없이 테스트 가능
 * - 다만 테스트의 속도가 느리므로, 다른 방법으로 테스트할 수 있다면 대안을 고려해보는 것이 좋음
 */
@SpringBootTest
class ProductRepositoryTest2 {
    @Autowired
    private ProductRepository productRepository;

    /*
    CRUD 모든 기능을 한 테스트 코드에 작성
    기본 메서드를 테스트하기 때문에 Given 구문을 한 번만 사용해 전체 테스트에 활용
     */
    @Test
    @DisplayName("@SpringBootTest 어노테이션을 사용한 CRUD 테스트")
    public void basicCRUDTest() {
        /* create */
        // given
        Product givenProduct = Product.builder()
                .name("노트")
                .price(1000)
                .stock(500)
                .build();

        // when
        Product savedProduct = productRepository.save(givenProduct);

        Assertions.assertThat(savedProduct.getNumber()).isEqualTo(givenProduct.getNumber());
        Assertions.assertThat(savedProduct.getName()).isEqualTo(givenProduct.getName());
        Assertions.assertThat(savedProduct.getPrice()).isEqualTo(givenProduct.getPrice());
        Assertions.assertThat(savedProduct.getStock()).isEqualTo(givenProduct.getStock());


        /* read */
        // when
        Product selectedProduct = productRepository.findById(savedProduct.getNumber())
                .orElseThrow(RuntimeException::new);

        // then
        Assertions.assertThat(selectedProduct.getNumber()).isEqualTo(givenProduct.getNumber());
        Assertions.assertThat(selectedProduct.getName()).isEqualTo(givenProduct.getName());
        Assertions.assertThat(selectedProduct.getPrice()).isEqualTo(givenProduct.getPrice());
        Assertions.assertThat(selectedProduct.getStock()).isEqualTo(givenProduct.getStock());


        /* update */
        // when
        Product foundProduct = productRepository.findById(selectedProduct.getNumber())
                .orElseThrow(RuntimeException::new);

        foundProduct.setName("장난감");

        Product updatedProduct = productRepository.save(foundProduct);

        // then
        assertEquals(updatedProduct.getName(), "장난감");


        /* delete */
        // when
        productRepository.delete(updatedProduct);

        // then
        assertFalse(productRepository.findById(selectedProduct.getNumber()).isPresent());
    }
}