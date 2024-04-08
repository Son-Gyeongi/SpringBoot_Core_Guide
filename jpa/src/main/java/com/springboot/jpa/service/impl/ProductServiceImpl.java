package com.springboot.jpa.service.impl;

import com.springboot.jpa.data.dao.ProductDAO;
import com.springboot.jpa.data.dto.ProductDto;
import com.springboot.jpa.data.dto.ProductResponseDto;
import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * ProductService 구현체
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * @Autowired를 지정한 생성자를 통해 의존성 주입 받음
     */
    private final ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * 조회 메서드
     */
    @Override
    public ProductResponseDto getProduct(Long number) {
        Product product = productDAO.selectProduct(number);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(product.getNumber());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStock(product.getStock());

        return productResponseDto;
    }

    /**
     * 저장 메서드
     */
    @Override
    public ProductResponseDto saveProduct(ProductDto productDto) {
        // DTO 객체를 통해 엔티티 객체를 생성해서 초기화
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // 저장 (DAO 객체로 전달)
        Product savedProduct = productDAO.insertProduct(product);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(savedProduct.getNumber());
        productResponseDto.setName(savedProduct.getName());
        productResponseDto.setPrice(savedProduct.getPrice());
        productResponseDto.setStock(savedProduct.getStock());

        return productResponseDto;
    }

    /**
     * 업데이트 메서드
     */
    @Override
    public ProductResponseDto changeProductName(Long number, String name) throws Exception {
        Product changedProduct = productDAO.updateProductName(number, name);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(changedProduct.getNumber());
        productResponseDto.setName(changedProduct.getName());
        productResponseDto.setPrice(changedProduct.getPrice());
        productResponseDto.setStock(changedProduct.getStock());

        return productResponseDto;
    }

    /**
     * 삭제 메서드
     */
    @Override
    public void deleteProduct(Long number) throws Exception {
        productDAO.deleteProduct(number);
    }
}
