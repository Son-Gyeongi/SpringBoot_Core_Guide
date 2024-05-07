package com.springboot.advanced_jpa.data.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springboot.advanced_jpa.data.entity.Product;
import com.springboot.advanced_jpa.data.entity.QProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

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


    /**
     * 기본적인 QueryDSL 사용하기
     */
    @PersistenceContext
    EntityManager entityManager;

    /*
    QueryDSL에 의해 생성된 Q도메인 클래스를 활용하는 코드
    JPAQuery 객체를 사용해서 코드를 작성하는 방법
     */
    @Test
    @DisplayName("JPAQuery를 활용한 QueryDSL 테스트 코드")
    void queryDslTest() {
        // QueryDSL을 사용하기 위해서 JPAQuery 객체를 사용 - JPAQuery는 엔티티 매니저(entityManager)를 활용해 생성
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        QProduct qProduct = QProduct.product;

        // JPAQuery는 빌더 형식으로 쿼리 작성
        List<Product> productList = query
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();
        // List 타입으로 값을 리턴받기 위해서 fetch() 메서드 사용
        // - 4.0.1 이전 버전의 QueryDSL을 설정한다면 list() 메서드 사용

        for (Product product : productList) {
            System.out.println("--------------------");
            System.out.println();
            System.out.println("Product Number" + product.getNumber());
            System.out.println("Product Name" + product.getName());
            System.out.println("Product Price" + product.getPrice());
            System.out.println("Product Stock" + product.getStock());
            System.out.println();
            System.out.println("--------------------");
        }
    }

    /*
    JPAQueryFactory를 활용해 쿼리를 작성
    JPAQuery를 사용했을 때와 달리 JPAQueryFactory는 select 절부터 작성 가능
     */
    @Test
    @DisplayName("JPAQueryFactory를 활용한 QueryDSL 테스트 코드")
    void queryDslTest2() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> productList = jpaQueryFactory
                .selectFrom(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (Product product : productList) {
            System.out.println("--------------------");
            System.out.println();
            System.out.println("Product Number" + product.getNumber());
            System.out.println("Product Name" + product.getName());
            System.out.println("Product Price" + product.getPrice());
            System.out.println("Product Stock" + product.getStock());
            System.out.println();
            System.out.println("--------------------");
        }
    }

    /*
    JPAQueryFactory를 활용해 쿼리를 작성
    JPAQuery를 사용했을 때와 달리 JPAQueryFactory는 select 절부터 작성 가능
    - 전체 칼럼을 조회하지 않고 일부만 조회하고 싶다면 selectFrom()이 아닌
    select()와 from() 메서드를 구분해서 사용하면 됨
     */
    @Test
    @DisplayName("JPAQueryFactory를 활용한 QueryDSL 테스트 코드")
    void queryDslTest3() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;

        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (String product : productList) {
            System.out.println("--------------------");
            System.out.println("Product Name" + product);
            System.out.println("--------------------");
        }

        List<Tuple> tupleList = jpaQueryFactory
                .select(qProduct.name, qProduct.price)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (Tuple product : tupleList) {
            System.out.println("--------------------");
            System.out.println("Product Name" + product.get(qProduct.name));
            System.out.println("Product Price" + product.get(qProduct.price));
            System.out.println("--------------------");
        }
    }


    /*
    JPAQueryFactory 빈을 활용한 테스트 코드
     */
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    @DisplayName("JPAQueryFactory 빈을 활용한 테스트 코드")
    void queryDslTest4() {
        QProduct qProduct = QProduct.product;

        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (String product : productList) {
            System.out.println("--------------------");
            System.out.println("Product Name" + product);
            System.out.println("--------------------");
        }
    }


    /*
    JPA Auditing 테스트
     */
    @Test
    public void auditingTest() {
        Product product = new Product();
        product.setName("펜");
        product.setPrice(1000);
        product.setStock(100);

        Product savedProduct = productRepository.save(product);

        System.out.println("productName : " + savedProduct.getName());
        System.out.println("createdAt : " + savedProduct.getCreatedAt());
        /*
        productName : 펜
        createdAt : 2024-05-07T18:58:43.234459200
        직접 일자를 기입하지 않았지만 정상적으로 데이터베이스에는 생성일자가 저장되었다.
         */
    }
}