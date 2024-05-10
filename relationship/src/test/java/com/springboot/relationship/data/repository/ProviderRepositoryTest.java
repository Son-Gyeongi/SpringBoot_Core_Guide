package com.springboot.relationship.data.repository;

import com.springboot.relationship.data.entity.Product;
import com.springboot.relationship.data.entity.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 수정된 공급업체(Provider) 엔티티를 가지고 연관된 엔티티(Product)의 값을 가져올 수 있는지 테스트
 */
@SpringBootTest
class ProviderRepositoryTest {
    // 내가 테스트할 때 필요한 거를 의존성 주입 받기
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProviderRepository providerRepository;

    @Test
    void relationshipTest() {
        /*
        Provider 엔티티 클래스는 Product 엔티티와의 연관관계에서 주인이 아니기 때문에 외래키를 관리할 수 없다.
        그래서 Provider를 등록한 후 각 Product에 객체를 설정하는 작업을 통해 데이터베이스에 저장
         */
        // 테스트 데이터 생성
        Provider provider = new Provider();
        provider.setName("ㅇㅇ상사");

        providerRepository.save(provider);

        Product product1 = new Product();
        product1.setName("펜");
        product1.setPrice(2000);
        product1.setStock(100);
        product1.setProvider(provider);

        Product product2 = new Product();
        product2.setName("가방");
        product2.setPrice(20000);
        product2.setStock(200);
        product2.setProvider(provider);

        Product product3 = new Product();
        product3.setName("노트");
        product3.setPrice(3000);
        product3.setStock(1000);
        product3.setProvider(provider);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> products = providerRepository.findById(provider.getId()).get().getProductList();

        for (Product product : products) {
            System.out.println(product);
        }
    }
}