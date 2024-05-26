package com.springboot.security.controller;

import com.springboot.security.data.dto.ChangeProductNameDto;
import com.springboot.security.data.dto.ProductDto;
import com.springboot.security.data.dto.ProductResponseDto;
import com.springboot.security.service.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * http://localhost:8080/product?number=1
     */
    @ApiOperation(value = "Product 조회")
    @GetMapping()
    public ResponseEntity<ProductResponseDto> getProduct(Long number) {
        ProductResponseDto productResponseDto = productService.getProduct(number);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

//    @ApiOperation(value = "Product 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token",
            required = true, dataType = "String", paramType = "header")
    })
    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductDto productDto) {
        long currentTime = System.currentTimeMillis();
        ProductResponseDto productResponseDto = productService.saveProduct(productDto);

        LOGGER.info("[createProduct] Response Time : {}ms", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @ApiOperation(value = "Product 이름 수정")
    @PutMapping()
    public ResponseEntity<ProductResponseDto> changeProductName(
            @ApiParam(value = "변경될 제품 번호 및 수정할 이름") @RequestBody ChangeProductNameDto changeProductNameDto
    ) throws Exception {
        ProductResponseDto productResponseDto = productService.changeProductName(changeProductNameDto.getNumber(), changeProductNameDto.getName());

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @ApiOperation(value = "Product 삭제")
    @DeleteMapping
    public ResponseEntity<String> deleteProduct(Long number) throws Exception {
        productService.deleteProduct(number);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
