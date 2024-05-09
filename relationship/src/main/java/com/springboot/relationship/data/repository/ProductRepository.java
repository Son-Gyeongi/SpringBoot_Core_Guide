package com.springboot.relationship.data.repository;

import com.springboot.relationship.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// Product 테이블과 매핑되는 Product 엔티티에 대한 인터페이스
public interface ProductRepository extends JpaRepository<Product, Long> {
    /*
    쿼리 메서드의 주제 키워드
     */
    // find...By 키워드를 활용한 쿼리 메서드 - 조회하는 기능을 수행
    Optional<Product> findByNumber(Long number);
    List<Product> findAllByName(String name);
    Product queryByNumber(Long number);

    // exists...By 키워드를 활용한 쿼리 메서드 - 특정 데이터가 존재하는지 확인하는 키워드
    boolean existsByNumber(Long number);

    // count...By 키워드를 활용한 쿼리 메서드 - 조회 쿼리 수행한 후 쿼리 결과로 나온 레코드의 개수 리턴
    long countByName(String name);

    // delete...By, remove...By 를 활용한 쿼리 메서드 - 삭제 쿼리를 수행
    void deleteByNumber(Long number);
    long removeByName(String name); // 삭제한 횟수 리턴

    // ...First<Number>..., ...Top<Number>... 키워드를 활용한 쿼리 메서드 - 쿼리를 통해 조회된 결괏값의 개수를 제한하는 키워드
    List<Product> findFirst5ByName(String name);
    List<Product> findTop10ByName(String name);

    
    /*
    쿼리 메서드의 조건자 키워드
     */
    // Is
    // Is, Equals 키워드를 사용한 쿼리 메서드 - 값의 일치를 조건으로 사용(Is는 생략되는 경우가 많음)
    // Is는 Equals와 동일한 기능을 수행
    // findByNumber 메서드와 동일하게 동작
    Product findByNumberIs(Long number);
    Product findByNumberEquals(Long number);

    // (Is)Not
    // Not 키워드를 사용한 쿼리 메서드 - 값의 불일치를 조건으로 사용(Is는 생략하고 Not키워드만 사용 가능)
    Product findByNumberIsNot(Long number);
    Product findByNumberNot(Long number);

    // (Is)Null, (Is)NotNull
    // Null 키워드를 사용한 쿼리 메서드 - 값이 null인지 검사
    List<Product> findByUpdatedAtNull();
    List<Product> findByUpdatedAtIsNull();
    List<Product> findByUpdatedAtNotNull();
    List<Product> findByUpdatedAtIsNotNull();
    
    // (Is)True, (Is)False
    // True, False 키워드를 사용한 쿼리 메서드 - boolean 타입으로 지정된 칼럼값을 확인
    // 현재 Product 엔티티에 boolean 타입을 사용하는 칼럼이 없기 때문에 에러 발생하니 참고만 하기
//    Product findByisActiveTrue();
//    Product findByisActiveIsTrue();
//    Product findByisActiveFalse();
//    Product findByisActiveIsFalse();
    
    // And, Or - 여러 조건을 묶을 때 사용
    // And, Or 키워드를 사용한 쿼리 메서드
    Product findByNumberAndName(Long number, String name);
    Product findByNumberOrName(Long number, String name);
    
    // (Is)GreaterThan, (Is)LessThan, (Is)Between
    // GreaterThan, LessThan, Between 키워드를 사용한 쿼리 메서드
    // 숫자나 datetime 칼럼을 대상으로 한 비교 연산에 사용할 수 있는 조건자 키워드
    // GreaterThan 초과, 경곗값 포함 Equal 키워드
    List<Product> findByPriceIsGreaterThan(Long price);
    List<Product> findByPriceGreaterThan(Long price);
    List<Product> findByPriceGreaterThanEqual(Long price);
    // LessThan 미만, 경곗값 포함 Equal 키워드
    List<Product> findByPriceIsLessThan(Long price);
    List<Product> findByPriceLessThan(Long price);
    List<Product> findByPriceLessThanEqual(Long price);
    // lowPrice 와 highPrice 사이
    List<Product> findByPriceIsBetween(Long lowPrice, Long highPrice);
    List<Product> findByPriceBetween(Long lowPrice, Long highPrice);

    // (Is)StartingWith(==StartsWith), (Is)EndingWith(==EndsWith), (Is)Containing(==Contains), (Is)Like
    // 칼럼값에서 일부 일치 여부를 확인하는 조건자 키워드
    // SQL 쿼리문에서 값의 일부를 포함하는 값을 추출할 때 사용하는 '%'키워드와 동일한 역할
    // 부분 일치 키워드를 사용한 쿼리 메서드
    List<Product> findByNameLike(String name);
    List<Product> findByNameIsLike(String name);
    // %name%
    List<Product> findByNameContains(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameIsContaining(String name);
    // name%
    List<Product> findByNameStartsWith(String name);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByNameIsStartingWith(String name);
    // %name
    List<Product> findByNameEndsWith(String name);
    List<Product> findByNameEndingWith(String name);
    List<Product> findByNameIsEndingWith(String name);
    
    
    /*
    정렬과 페이징 처리
     */
    // 정렬 처리하기
    // 쿼리 메서드의 정렬 처리 - OrderBy<정렬하고자 하는 칼럼>
    // Asc : 오름차순, Desc : 내림차순
    List<Product> findByNameOrderByNumberAsc(String name);
    List<Product> findByNameOrderByNumberDesc(String name);
    
    // 쿼리 메서드에서 여러 정렬 기준 사용 - 우선순위를 기준으로 차례대로 작성
    // And를 붙이지 않음
    List<Product> findByNameOrderByPriceAscStockDesc(String name);

    // 위처럼 쿼리 메서드의 이름에 정렬 키워드를 삽입해서 정렬을 수행할 때 너무 길면 가독성이 떨어짐
    // 매개변수를 활용한 쿼리 정렬 => Sort 객체를 활용한 테스트 있음
    List<Product> findByName(String name, Sort sort);


    // 페이징 처리하기
    // 페이징 - 데이터베이스의 레코드를 개수로 나눠 페이지를 구분하는 것
    // JPA에서는 페이징 처리를 위해 Page와 Pageable을 사용
    // 페이징 처리를 위한 쿼리 메서드 => 예시 테스트 있음
    Page<Product> findByName(String name, Pageable pageable);


    // @Query 어노테이션 사용하기
    // @Query 어노테이션을 사용하는 메서드
//    @Query("SELECT p FROM Product AS p WHERE p.name = ?1")
//    List<Product> findByName(String name);

    // @Query 어노테이션과 @Param 어노테이션을 사용한 메서드
    @Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> findByName(@Param("name") String name);

    // 특정 칼럼만 추출하는 쿼리
    @Query("SELECT p.name, p.price, p.stock FROM Product p WHERE p.name = :name")
    List<Object[]> findByNameParam2(@Param("name") String name);
}
