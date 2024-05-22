package com.springboot.rest.service;

import com.springboot.rest.dto.MemberDto;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * RestTemplate 구현하기
 * 일반적으로 RestTemplate은 별도의 유틸리티 클래스로 생성하거나 서비스 또는 비즈니스 계층에 구현됨
 */
@Service
public class RestTemplateService {

    /*
    GET 형식의 RestTemplate 작성하기
     */
    // PathVariable이나 파라미터를 사용하지 않는 호출 방법
    public String getName() {
        /*
        UriComponentsBuilder는 스프링 프레임워크에서 제공하는 클래스로 여러 파라미터를 연결해서 URI 형식으로 만드는 기능
         */
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090") // 호출부의 URL
                .path("/api/v1/crud-api") // 세부 경로
                .encode() // 인코딩 문자셋 설정, 기본 UTF-8
                .build() // 빌더 생성 종료
                .toUri(); // URI 타입으로 리턴

        RestTemplate restTemplate = new RestTemplate();
        // getForEntity - GET 형식으로 요청한 결과를 ResponseEntity 형식으로 반환
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class); // getForEntity()는 URI, 응답받는 타입을 매개변수로 사용

        return responseEntity.getBody();
    }

    // PathVariable로 전달하는 예제
    public String getNameWithPathVariable() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/v1/crud-api/{name}")
                .encode()
                .build()
                .expand("Flature") // 복수의 값을 넣어야 할 경우 ,(콤마)를 추가하여 구분
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        return responseEntity.getBody();
    }

    // 파라미터로 전달하는 예제
    public String getNameWithParameter() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/v1/crud-api/param")
                .queryParam("name", "Flature") // 키, 값 형식으로 파라미터를 추가
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        return responseEntity.getBody();
    }


    /*
    POST 형식의 RestTemplate 작성하기
     */
    // POST 현식으로 외부 API에 요청할 때 Body값과 파라미터 값을 담는 방법 두 가지를 모두 보여줌
    public ResponseEntity<MemberDto> postWithParamAndBody() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/v1/crud-api")
                .queryParam("name", "Flature") // 파라미터에 값을 추가하는 작업
                .queryParam("email", "flature@wikibooks.co.kr")
                .queryParam("organization", "Wikibooks")
                .encode()
                .build()
                .toUri();

        // RequestBody에 값을 담는 작업
        // RequestBody에 값을 담기 위해서 데이터 객체 생성
        MemberDto memberDto = new MemberDto();
        memberDto.setName("flature!!");
        memberDto.setEmail("flature@gmail.com");
        memberDto.setOrganization("Around Hub Studio");

        RestTemplate restTemplate = new RestTemplate();
        // postForEntity() - POST 형식으로 요청한 결과를 ResponseEntity 형식으로 반환
        ResponseEntity<MemberDto> responseEntity = restTemplate.postForEntity(uri, memberDto, MemberDto.class);

        return responseEntity;
    }

    public ResponseEntity<MemberDto> postWithHeader() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/v1/crud-api/add-header")
                .encode()
                .build()
                .toUri();

        MemberDto memberDto = new MemberDto();
        memberDto.setName("flature");
        memberDto.setEmail("flature@wikibooks.co.kr");
        memberDto.setOrganization("Around Hub Studio");

        /*
        대부분 외부 API는 토큰키를 받아 서비스 접근을 인증하는 방식으로 작동
        - 토큰값을 헤더에 담아 전달하는 방식이 가장 많이 사용됨
        - 헤더를 설정하기 위해서 RequestEntity를 정의해서 사용하는 방법이 가장 편한 방법
        - 대체로 서버 프로젝트의 API 명세에는 헤더에 필요한 키값을 요구하면서 키 이름을 함께 제시하기 때문에
        그에 맞춰 헤더 값을 설정하면 됨
         */
        RequestEntity<MemberDto> requestEntity = RequestEntity
                .post(uri)
                .header("my-header", "Wikibooks API")
                .body(memberDto);

        RestTemplate restTemplate = new RestTemplate();
        // exchange() - HTTP 헤더를 임의로 추가할 수 있고, 어떤 메서드 형식에서도 사용할 수 있음
        ResponseEntity<MemberDto> responseEntity = restTemplate.exchange(requestEntity, MemberDto.class);

        return responseEntity;
    }


    /*
    커스텀 RestTemplate 객체 생성 메서드
     */
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        // httpClient를 생성하는 방법 첫번째 - 커넥션 풀 설정
        HttpClient client = HttpClientBuilder.create()
                .setMaxConnTotal(500)
                .setMaxConnPerRoute(500)
                .build();

        // httpClient를 생성하는 방법 두번째 - 커넥션 풀 설정
        // CloseableHttpClient - HttpClient의 구현체 / 사용 후에는 모든 인스턴스를 닫아야 한다.(리소스를 확보하기 위해 인스턴스를 닫아야 한다)
        // HttpClients는 CloseableHttpClient 인스턴스를 만들기 위한 팩터리 메서드를 포함하는 유틸리티 클래스
        // 4.3 버전 이후부터 기본으로 사용되는 HTTP 클라이언트 구현체
        CloseableHttpClient httpClient = HttpClients.custom()
                .setMaxConnTotal(500)
                .setMaxConnPerRoute(500)
                .build();

        factory.setHttpClient(httpClient);
        factory.setConnectTimeout(200); // RestTemplate의 Timeout 설정
        factory.setReadTimeout(5000); // RestTemplate의 Timeout 설정

        RestTemplate restTemplate = new RestTemplate(factory);

        return restTemplate;
    }
}
