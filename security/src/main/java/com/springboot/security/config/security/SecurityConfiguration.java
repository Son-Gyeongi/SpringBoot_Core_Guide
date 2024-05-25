package com.springboot.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 스프링 시큐리티와 관련된 설정
 * WebSecurityConfigureAdapter를 상속받는 Configuration 클래스 구현하기
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*
    HttpSecurity를 설정하는 configure() 메서드
    스프링 시큐리티의 설정은 대부분 HttpSecurity를 통해 진행한다.
    - 리소스 접근 권한 설정
    - 인증 실패 시 발생하는 예외 처리
    - 인증 로직 커스터마이징
    - csrf, cors 등의 스프링 시큐리티 설정
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 모든 설정은 전달 받은 HttpSecurity에 설정하게 된다.
        // .httpBasic().disable() - UI를 사용하는 것을 기본값으로 가진 시큐리티 설정을 비활성화 한다.
        httpSecurity.httpBasic().disable()

                // REST API에서는 CSRF 보안이 필요 없기 때문에 비활성화하는 로직
                .csrf().disable()

                // REST API 기반 애플리케이션의 동작 방식을 설정
                // 현재 진행 중인 프로젝트에서는 JWT 토큰으로 인증을 처리하며, 세션은 사용하지 않기 때문에 SATELESS로 설정
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)

                /*
                authorizeRequest()
                - 애플리케이션에 들어오는 요청에 대한 사용 권한을 체크
                antMatchers()
                - antPattern을 통해 권한을 설정하는 역할
                 */
                .and()
                .authorizeRequests()
                .antMatchers("/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception").permitAll() // 해당 경로에 대해서 모두 허용
                .antMatchers(HttpMethod.GET, "/product/**").permitAll() // /product 로 시작하는 경로의 GET 요청은 모두 허용
                // import org.springframework.http.HttpMethod;

                .antMatchers("**exception**").permitAll() // exception 단어가 들어간 경로는 모두 허용

                .anyRequest().hasRole("ADMIN") // 기타 요청은 인증된 권한을 가진 사용자에게 허용

                /*
                인증과 인가 과정의 예외 상황
                각 메서드는 CustomAccessDeniedHandler와 CustomAuthenticationEntryPoint로 예외를 전달한다.
                 */
                .and()
                // 권한을 확인하는 과정에서 통과하지 못하는 예외가 발생할 경우 예외를 전달한다.
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                // 인증 과정에서 예외가 발생할 경우 예외를 전달한다.
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                /*
                addFilterBefore() 메서드를 사용해 어느 필터 앞에 추가할 것인지 설정할 수 있다.

                현재 설정은 스프링 시큐리티에서 인증을 처리하는 필터인 UsernamePasswordAuthenticationFilter 앞에
                JwtAuthenticationFilter를 추가하겠다는 의미이다.
                - 추가된 필터에서 인증이 정상적으로 처리되면 UsernamePasswordAuthenticationFilter 는 자동으로 통과된다.
                 */
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }

    /*
    WebSecurity를 사용하는 configure() 메서드
    WenSecurity는 HttpSecurity 앞단에 적용되며, 전체적으로 스프링 시큐리티의 영향권 밖에 있다.
    - 인증과 인가가 모두 적용되기 전에 동작하는 설정
    - 다양한 곳에서 사용되지 않고 인증과 인가가 적용되지 않는 리소스 접근에 대해서만 사용

    아래에는 Swagger에 적용되는 인증과 인가를 피하기 위해 ignoring() 메서드를 사용해 Swagger와 관련된 경로에 대한 예외 처리를 수행
    - 인증, 인가를 무시하는 경로를 설정
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception");
    }
}
