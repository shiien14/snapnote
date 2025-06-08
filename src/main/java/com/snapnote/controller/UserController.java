package com.snapnote.controller;

import com.snapnote.dto.user.MypageResponseDto;
import com.snapnote.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<MypageResponseDto> getMypage(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername(); // JWT 인증 필터에서 설정된 사용자 이메일
        MypageResponseDto response = userService.getMypage(email);
        return ResponseEntity.ok(response);
    }
}