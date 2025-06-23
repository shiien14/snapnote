package com.snapnote.controller;

import com.snapnote.dto.user.MypageResponseDto;
import com.snapnote.dto.user.UpdateUserRequestDto;
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
    public ResponseEntity<MypageResponseDto> getMypage(@AuthenticationPrincipal String email) {
        MypageResponseDto response = userService.getMypage(email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<MypageResponseDto> updateUserName(
            @AuthenticationPrincipal String email,
            @RequestBody UpdateUserRequestDto request
    ) {
        return ResponseEntity.ok(userService.updateUserName(email, request));
    }
}