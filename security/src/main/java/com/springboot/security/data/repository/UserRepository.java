package com.springboot.security.data.repository;

import com.springboot.security.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // id값(사용자 아이디(uid))을 토큰 생성 정보로 사용하기 위해 생성(현재 id 필드 이름인 ID값-인덱스값말고)
    User getByUid(String uid);
}
