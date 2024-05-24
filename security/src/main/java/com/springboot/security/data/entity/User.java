package com.springboot.security.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User 엔티티 - 사용자 정보를 담는 엔티티
 * User 엔티티는 UserDetails 인터페이스를 구현하고 있다.
 * User 엔티티는 앞으로 토큰을 생성할 때 토큰의 정보로 사용될 정보와 권한 정보를 갖게 된다.
 *
 * UserDetails는 UserDetailsService를 통해 입력된 로그인 정보를 가지고
 * 데이터베이스에서 사용자 정보를 가져오는 역할을 수행
 *
 * 이번 예제는 계정의 상태 변경은 다루지 않을 예정이므로 true를 리턴한다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true) // null 허용하지 않고 중복이면 안됨
    private String uid;

    /*
    // JSON 직렬화/역직렬화 시 필드의 접근 //
    @JsonProperty는 Jackson 라이브러리에서 제공하는 어노테이션으로, JSON 직렬화 및 역직렬화 시 필드의 접근 방법을 제어

    직렬화(Serialization): 객체를 JSON 문자열로 변환하는 과정.
    역직렬화(Deserialization): JSON 문자열을 객체로 변환하는 과정.

    access = JsonProperty.Access.WRITE_ONLY: 이 설정은 해당 필드를 JSON으로 직렬화할 때는 무시하고, JSON에서 역직렬화할 때만 사용하도록 함
    즉, API 응답에는 이 필드가 포함되지 않고, 요청을 처리할 때만 사용
    이는 보안이나 개인정보 보호를 위해 주로 사용됨
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    /*
    // JPA 엔티티의 컬렉션 매핑 //
    @ElementCollection은 JPA에서 컬렉션을 엔티티와 함께 매핑할 때 사용
    주로 List, Set 같은 컬렉션을 엔티티의 속성으로 사용할 때 적용

    fetch = FetchType.EAGER: fetch 속성은 엔티티를 조회할 때 관련된 컬렉션을 언제 로드할지를 결정
    FetchType.EAGER로 설정하면 엔티티가 로드될 때 컬렉션도 즉시 로드됨
    기본적으로 지연 로딩(FetchType.LAZY)보다 성능에 영향을 미칠 수 있음

    // 객체 생성 시 기본값 설정 //
    @Builder는 Lombok 라이브러리에서 제공하는 어노테이션입니다.
    @Builder 어노테이션을 사용해 빌더 패턴을 적용할 때, 필드에 기본값을 지정할 수 있도록 함

    @Builder.Default는 빌더 패턴으로 객체를 생성할 때 특정 필드에 기본값을 설정하고 싶을 때 사용
    그렇지 않으면, 빌더는 필드의 초기값을 무시하게 됨
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    /*
    UserDetails에서 각 메서드의 용도

    // getPassword() : 계정의 비밀번호를 리턴한다.
     */
    // getAuthorities() : 계정이 가지고 있는 권한 목록을 리턴한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    // getUsername : 계정의 이름을 리턴한다. 일반적으로 아이디를 리턴한다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    // isAccountNonExpired() : 계정이 만료됐는지 리턴한다. true는 만료되지 않았다는 의미이다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // isAccountNonLocked() : 계정이 잠겨있는지 리턴한다. true는 잠기지 않았다는 의미이다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // isCredentialsNonExpired() : 비밀번호가 만료됐는지 리턴한다. true는 만료되지 않았다는 의미이다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // isEnabled() : 계정이 활성화돼 있는지 리턴한다. true는 활성화 상태를 의미한다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
