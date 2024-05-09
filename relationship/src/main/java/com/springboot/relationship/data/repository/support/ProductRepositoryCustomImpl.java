package com.springboot.relationship.data.repository.support;

import com.springboot.relationship.data.entity.Product;
import com.springboot.relationship.data.entity.QProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProductRepositoryCustom에서 정의된 메서드를 기반으로 실제 쿼리 작성을 하기 위해
 * 구현체인 ProductRepositoryCustomImpl 클래스 생성
 *
 * QueryDSL을 사용하기 위해 QuerydslRepositorySupport 추상클래스를 상속 받는다.
 */
@Component
public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {
    // QuerydslRepositorySupport를 상속받으면 생성자를 통해 도메인 클래스를 부모 클래스에 전달해야 한다.
    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }

    @Override // 인터페이스에 정의한 메서드를 구현
    public List<Product> findByName(String name) {
        // Q도메인 클래스인 QProduct를 사용해 QuerydslRepositorySupport가 제공하는 기능을 사용한다.
        QProduct product = QProduct.product;

        List<Product> productList = from(product)
                .where(product.name.eq(name))
                .select(product)
                .fetch();

        return productList;
    }
}
