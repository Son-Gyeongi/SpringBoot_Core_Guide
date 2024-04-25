package com.springboot.test.service.impl;

import com.springboot.test.data.dto.ProductDto;
import com.springboot.test.data.dto.ProductResponseDto;
import com.springboot.test.data.entity.Product;
import com.springboot.test.data.repository.ProductRepository;
import com.springboot.test.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/*
단위 테스트
- 단위 테스트를 위해서는 외부 요인을 모두 배제하도록 코드를 작성해야 함
 */
/*
@ExtendWith(SpringExtension.class)
- 스프링에서 객체를 주입받기 위해 사용
- SpringExtension 클래스는 JUnit5의 Jupiter 테스트에
  스프링 테스트 컨텍스트 프레임워크를 통합하는 역할 수행
 */
@ExtendWith(SpringExtension.class)
@Import({ProductServiceImpl.class}) // @Autowired어노테이션으로 주입받는 ProductService를 주입받기 위해 사용
class ProductServiceTest2 {
    /*
    ** Mock 객체를 활용한 테스트 방식2 **
    @MockBean 어노테이션을 사용한 테스트 환경 설정
    스프링에서 제공하는 테스트 어노테이션을 통해 Mock 객체를 생성하고 의존성 주입을 받음
     */
    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    void getProductTest() {
        // given
        Product givenProduct = new Product();
        givenProduct.setNumber(123L);
        givenProduct.setName("펜");
        givenProduct.setPrice(1000);
        givenProduct.setStock(1234);

        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(givenProduct));

        // when
        ProductResponseDto productResponseDto = productService.getProduct(123L);

        // then
        Assertions.assertEquals(productResponseDto.getNumber(), givenProduct.getNumber());
        Assertions.assertEquals(productResponseDto.getName(), givenProduct.getName());
        Assertions.assertEquals(productResponseDto.getPrice(), givenProduct.getPrice());
        Assertions.assertEquals(productResponseDto.getStock(), givenProduct.getStock());

        // 검증
        verify(productRepository).findById(123L);
    }

    @Test
    void saveProductTest() {
        // given
        // returnsFirstArg() : Mockito input을 그대로 리턴하기
        Mockito.when(productRepository.save(any(Product.class))).then(returnsFirstArg());

        // when
        ProductResponseDto productResponseDto = productService.saveProduct(new ProductDto("펜", 1000, 1234));

        // then
        Assertions.assertEquals(productResponseDto.getName(), "펜");
        Assertions.assertEquals(productResponseDto.getPrice(), 1000);
        Assertions.assertEquals(productResponseDto.getStock(), 1234);

        // 검증
        verify(productRepository).save(any());
    }
}