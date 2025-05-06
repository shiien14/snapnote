package com.snapnote.service;

import com.snapnote.config.jwt.JwtProvider;
import com.snapnote.domain.User;
import com.snapnote.dto.auth.LoginRequest;
import com.snapnote.dto.auth.LoginResponse;
import com.snapnote.dto.auth.SignupRequest;
import com.snapnote.dto.auth.SignupResponse;
import com.snapnote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * 회원가입 로직
     */
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();

        User savedUser = userRepository.save(user);

        return new SignupResponse(savedUser.getId(), "회원가입이 완료되었습니다.");
    }

    /**
     * 로그인 로직
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // JWT 발급 로직은 나중에 추가
        String fakeToken = jwtProvider.generateToken(user.getEmail());

        return new LoginResponse(fakeToken, user.getNickname());
    }
}
