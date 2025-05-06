package com.snapnote.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
// import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
public class LoginResponse {

    // @Schema(description = "JWT 토큰")
    private String token;

    // @Schema(description = "유저 닉네임")
    private String nickname;
}
