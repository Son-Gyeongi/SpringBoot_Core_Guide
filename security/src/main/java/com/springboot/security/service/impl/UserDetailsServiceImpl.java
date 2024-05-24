package com.springboot.security.service.impl;

import com.springboot.security.data.repository.UserRepository;
import com.springboot.security.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetails는 스프링 시큐리티에서 제공하는 개념
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    // UserDetailsServiceImpl 클래스 로그 작성하겠다.
    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    // 요청 받은 username으로 사용자를 찾고, 해당 사용자의 정보를 Spring Security에 제공하는 역할
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username은 각 사용자를 구분할 수 있는 ID(uid)를 의미
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        
        // UserDetails의 구현체로 User 엔티티를 생성했기 때문에 User 객체를 리턴하게끔 구현한 것
        return userRepository.getByUid(username);
    }
}
