package com.springboot.test.data.repository;

import com.springboot.test.data.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

/*
리포지토리는 개발자가 구현하는 레이어 중에서 가장 데이터베이스오 가깝다.
리포지토리 테스트는 구현하는 목적에 대해 고민하고 작성해야 한다.
데이터베이스를 연동한 테스트는 테스트 데이터를 제거하는 코드까지 포함해서 작성하는 것이 좋다.
 */
/*
@DataJpaTest
- JPA와 관련된 설정만 로드해서 테스트 진행
- 기본적으로 @Transactional 어노테이션을 포함하고 있어 테스트 코드가 종료되면 자동으로 데이터베이스의 롤백이 진행
- 기본값으로 임베디드 데이터베이스 사용, 다른 데이터베이스 사용하려면 별도의 설정을 거쳐 사용 가능
 */
@DataJpaTest
/*
테스트 데이터베이스 변경을 위한 어노테이션 추가
Replace.ANY 는 기본값으로 임베디드 메모리 데이터베이스를 사용
Replace.NONE 은 애플리케이션에서 실제로 사용하는 데이터베이스로 테스트 가능
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductRepositoryTest {
    // @DataJpaTest 어노테이션을 선언해서 리포지토리를 정상적으로 주입받을 수 있다.
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("데이터베이스 저장 테스트")
    void saveTest() {
        // given
        Product product = new Product();
        product.setName("펜");
        product.setPrice(1000);
        product.setStock(1000);

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getStock(), savedProduct.getStock());
    }

    @Test
    @DisplayName("데이터베이스 조회 테스트")
    void selectTest() {
        // given
        Product product = new Product();
        product.setName("펜");
        product.setPrice(1000);
        product.setStock(1000);

        // 데이터베이스에 테스트 데이터를 추가
        Product savedProduct = productRepository.saveAndFlush(product);

        // when
        Product foundProduct = productRepository.findById(savedProduct.getNumber()).get();

        // then
        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(product.getPrice(), foundProduct.getPrice());
        assertEquals(product.getStock(), foundProduct.getStock());
    }
}