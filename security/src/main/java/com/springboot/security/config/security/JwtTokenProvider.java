package com.springboot.security.config.security;

import com.springboot.security.service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component // 애플리케이션이 가동되면서 빈으로 자동 주입 됨
@RequiredArgsConstructor
public class JwtTokenProvider {

    // JwtTokenProvider 클래스 로그 작성하겠다.
    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    @Value("${springboot.jwt.secret}") // org.springframework.beans.factory.annotation.Value
    private String secretKey = "secretKey"; // 토큰을 생성하기 위해서 시크릿키 필요
    private final long tokenValidMillisecond = 1000L * 60 * 60;

    @PostConstruct // 해당 객체가 빈 객체로 주입된 이후 수행되는 메서드를 가리킨다.
    protected void init() {
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        // secretKey를 Base64 형식으로 인코딩한다.
        System.out.println(secretKey); // 인코딩 전 원본 문자열: flature!@#
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(secretKey); // Base64 인코딩 결과: ZmxhdHVyZSFAIw==
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createToken(String userUid, List<String> roles) {
        LOGGER.info("[createToken] 토큰 생성 시작");
        // JWT 토큰의 내용에 값을 넣기 위해 Claims 객체 생성
        Claims claims = Jwts.claims().setSubject(userUid); // sub 속성에 값을 추가하려면 User의 uid 값을 사용
        claims.put("roles", roles); // 해당 토큰을 사용하는 사용자의 권한을 확인할 수 있는 role 값을 별개로 추가
        Date now = new Date();

        // 토큰 생성
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    // 필터에서 인증이 성공했을 때 SecurityContextHolder에 저장할 Authentication을 생성하는 역할
    public Authentication getAuthentication(String token) {
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        // UsernamePasswordAuthenticationToken 토큰 클래스를 사용하려면 초기화를 위한 UserDetails가 필요
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        // Authentication을 구현하는 방법은 UsernamePasswordAuthenticationToken을 사용하는 것
    }

    // username 값(uid) 리턴
    public String getUsername(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        // Jwts.parser()를 통해 secretKey를 설정하고 클레임을 추출해서 토큰을 생성할 때 넣었던 sub 값을 추출한다.
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }

    // HttpServletRequest를 파라미터로 받아 헤더 값으로 전달된 'X-AUTH-TOKEN' 값을 가져와 리턴
    // 클라이언트가 헤더를 통해 애플리케이션 서버로 JWT 토큰 값을 전달해야 정상적인 추출이 가능
    // 'X-AUTH-TOKEN' 헤더의 이름은 임의로 변경 가능
    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰을 전달받아 클레임의 유효기간을 체크하고 boolean 타입의 값을 리턴하는 역할
    public boolean validateToken(String token) {
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try {
            // 토큰에서 claims 정보를 추출
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            /*
            claims.getBody().getExpiration()은 claims 객체에서 만료 날짜(Expiration)를 가져온다.
            getExpiration().before(new Date())는 만료 날짜가 현재 날짜(new Date()) 이전인지 확인한다.
            만약 만료 날짜가 현재 날짜 이전이라면 before(new Date())는 true를 반환
            그런데 ! 연산자는 이 결과를 반대로 만든다.
            즉, 만료 날짜가 현재 날짜 이전이라면 false를 반환하고, 만료 날짜가 현재 날짜 이후거나 같은 경우 true를 반환

            설명
            // claims 정보에서 만료 날짜를 가져온다. 만료 날짜가 현재 날짜 이전이면 true를 반환하지만,
            // ! 연산자로 인해 결과를 반대로 만들어 만료 날짜가 현재 날짜 이후거나 같으면 true를 반환하고,
            // 만료 날짜가 현재 날짜 이전이면 false를 반환한다.
             */
            // claims의 만료 날짜가 현재 날짜 이후거나 같은지 확인하여, 그렇다면 true(아직 유효하다)를 반환하고, 그렇지 않다면 false(만료되었다)를 반환
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }
}
