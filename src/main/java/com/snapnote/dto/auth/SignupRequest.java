package com.snapnote.dto.auth;

//import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    //@Schema(description = "사용자 이메일", example = "user@example.com")
    @NotBlank
    @Email
    private String email;

    //@Schema(description = "비밀번호", example = "pass1234")
    @NotBlank
    private String password;

    //@Schema(description = "닉네임", example = "시은")
    @NotBlank
    private String nickname;
}
