package com.snapnote.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
// import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
public class SignupResponse {

    // @Schema(description = "회원가입된 사용자 ID")
    private Long userId;

    // @Schema(description = "가입 성공 메시지")
    private String message;
}
