package com.snapnote.repository;

import com.snapnote.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 중복 확인용
    boolean existsByEmail(String email);

    // 로그인용 (이메일로 사용자 조회)
    Optional<User> findByEmail(String email);
}
