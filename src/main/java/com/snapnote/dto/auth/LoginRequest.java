package com.snapnote.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
// import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class LoginRequest {

    // @Schema(description = "이메일", example = "user@example.com")
    @NotBlank
    private String email;

    // @Schema(description = "비밀번호", example = "pass1234")
    @NotBlank
    private String password;
}
