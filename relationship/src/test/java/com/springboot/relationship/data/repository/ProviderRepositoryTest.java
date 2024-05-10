package com.springboot.relationship.data.repository;

import com.springboot.relationship.data.entity.Product;
import com.springboot.relationship.data.entity.Provider;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    @DisplayName("영속성 전이 테스트")
    void cascadeTest() {
        Provider provider = savedProvider("새로운 공급업체");

        Product product1 = savedProduct("상품1", 1000, 1000);
        Product product2 = savedProduct("상품2", 500, 1500);
        Product product3 = savedProduct("상품3", 750, 500);

        // 연관관계 설정
        product1.setProvider(provider);
        product2.setProvider(provider);
        product3.setProvider(provider);

        provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));

        providerRepository.save(provider); // 영속성 전이 수행
    }

    private Product savedProduct(String name, Integer price, Integer stock) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);

        return product; // 영속성 전이 테스트를 위해서 영속화 작업을 수행하지 않음 - repository에 save 하지 않음
    }

    private Provider savedProvider(String name) {
        Provider provider = new Provider();
        provider.setName(name);

        return provider; // 영속성 전이 테스트를 위해서 영속화 작업을 수행하지 않음 - repository에 save 하지 않음
    }

    @Test
    @Transactional // 트랜잭션 유지로 영속성 컨텍스트에서 관리
    @DisplayName("고아 객체의 제거 기능 테스트")
    void orphanRemovalTest() {
        Provider provider = savedProvider("새로운 공급업체");

        Product product1 = savedProduct("상품1", 1000, 1000);
        Product product2 = savedProduct("상품2", 500, 1500);
        Product product3 = savedProduct("상품3", 750, 500);

        // 연관관계 설정
        product1.setProvider(provider);
        product2.setProvider(provider);
        product3.setProvider(provider);

        provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));

        providerRepository.saveAndFlush(provider); // saveAndFlush() : 변경 내용을 즉시 데이터베이스에 반영

        providerRepository.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);

        /*
        고아 객체를 생성하기 위해 126~127번 줄에 생성한 공급업체 엔티티를 가져온 후 첫번째로 매핑돼 있는 상품 엔티티의 연관관계를 제거
         */
        Provider foundProvider = providerRepository.findById(1L).get();
        foundProvider.getProductList().remove(0);

        providerRepository.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println); // 연관관계가 끊긴 상품의 엔티티가 제거된 것을 확인 가능
    }
}