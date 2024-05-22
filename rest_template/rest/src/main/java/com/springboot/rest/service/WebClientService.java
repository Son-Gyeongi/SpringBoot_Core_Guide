package com.springboot.rest.service;

import com.springboot.rest.dto.MemberDto;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 현재 실습은 리액티브 프로그래밍을 기반으로 작성된 애플리케이션이 아니기 때문에 WebClient를 온전히 사용하기에는 제약이 있습니다.
 *
 * Spring WebFlux는 HTTP 요청을 수행하는 클라이언트로 WebClient를 제공합니다.
 * 웹 통신 - WebClient 사용하기
 *
 * - WebClient 공식 문서
 * https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html
 */
@Service
public class WebClientService {

    /*
    WebClient를 활용한 GET 요청 예제
     */
    // WebClient를 생성하는 첫번째 방법 - builder()를 이용한 생성
    public String getName() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get()
                .uri("/api/v1/crud-api")
                .retrieve() // 요청에 대한 응답을 받았을 때 그 값을 추출하는 방법 중 하나
                .bodyToMono(String.class) // 리턴 타입 설정
                .block();
        // WebClient는 기본적으로 논블로킹(Non-Blocking) 방식으로 동작하기 때문에 기존에 사용하던 코드의 구조를
        // 블로킹 구조로 바꿔줄 필요가 있습니다. block() 메서드를 추가해서 블로킹 형식으로 동작하게끔 설정했습니다.
    }

    // WebClient를 생성하는 두번째 방법 - create() 메서드를 이용한 생성
    // PathVariable 값을 추가해 요청을 보내는 예제
    public String getNameWithPathVariable() {
        WebClient webClient = WebClient.create("http://localhost:9090");

        ResponseEntity<String> responseEntity = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/{name}")
                        .build("Flature"))
                .retrieve().toEntity(String.class).block(); // bodyToMoNo()가 아닌 toEntity()를 사용하면 ResponseEntity타입으로 응답 받을 수 있습니다.

        /*
        위에 코드를 좀 더 간략하게 작성하는 방법
        ResponseEntity<String> responseEntity1 = webClient.get()
                .uri("/api/v1/crud-api/{name}", "Flature")
                .retrieve()
                .toEntity(String.class)
                .block();
        */

        return responseEntity.getBody();
    }

    // WebClient를 생성하는 두번째 방법 - create() 메서드를 이용한 생성
    // 쿼리 파라미터를 함께 전달하는 방법
    public String getNameWithParameter() {
        WebClient webClient = WebClient.create("http://localhost:9090");

        return webClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api")
                        .queryParam("name", "Flature")
                        .build())
                .exchangeToMono(clientResponse -> { // retrieve() 대신 여기서는 exchangeToMono() 사용 - 응답 결과 코드에 따라 다르게 응답을 설정 가능
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                })
                .block();
    }


    /*
    WebClient를 활용한 POST 요청 예제
     */
    // POST 요청을 보낼 떄 Body값과 파라미터 보내는 예제
    public ResponseEntity<MemberDto> postWithParamAndBody() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        MemberDto memberDto = new MemberDto();
        memberDto.setName("flature!!");
        memberDto.setEmail("flature@gmail.com");
        memberDto.setOrganization("Around Hub Studio");

        return webClient.post().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api")
                        .queryParam("name", "Flature")
                        .queryParam("email", "flature@wikibooks.co.kr")
                        .queryParam("organization", "Wikibooks")
                        .build())
                .bodyValue(memberDto) // HTTP 바디 값을 담는 방법, HTTP 바디에는 일반적으로 데이터 객체(DTO, VO 등)를 파라미터로 전달
                .retrieve()
                .toEntity(MemberDto.class)
                .block();
    }

    // POST 요청을 보낼 떄 헤더를 추가해서 보내는 예제
    public ResponseEntity<MemberDto> postWithHeader() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        MemberDto memberDto = new MemberDto();
        memberDto.setName("flature!!");
        memberDto.setEmail("flature@gmail.com");
        memberDto.setOrganization("Around Hub Studio");

        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/add-header")
                        .build())
                .bodyValue(memberDto)
                // 일반적으로 임의로 추가한 헤더에는 외부 API를 사용하기 위해 인증된 토큰값을 담아 전달합니다.
                .header("my-header", "Wikibooks API") // 커스텀 헤더를 추가하는 방법
                .retrieve()
                .toEntity(MemberDto.class)
                .block();
    }
}
