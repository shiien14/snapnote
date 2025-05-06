package com.snapnote.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Collections;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println(">> JWT 필터 동작 시작");

        // 1. 요청 헤더에서 JWT 꺼내기
        String token = resolveToken(request);
        System.out.println(">> 요청에서 꺼낸 토큰: " + token);
        // 2. 토큰 유효성 검사
        if (token != null && jwtProvider.validateToken(token)) {
            String email = jwtProvider.getEmail(token); // 토큰에서 이메일 추출

            System.out.println(">> 토큰 유효함 ");
            System.out.println(">> 토큰에서 꺼낸 이메일: " + email);

            // 3. 사용자 인증 정보 설정 (우린 DB 조회 없이 간단 인증)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            // 4. 인증 정보를 SecurityContext에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println(">> 인증 객체 설정 완료: " +
                    SecurityContextHolder.getContext().getAuthentication());
        } else {
            System.out.println(">> 토큰이 없거나 유효하지 않음 ");
        }

        // 다음 필터로 넘어가기
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // "Bearer {TOKEN}" 형식 확인
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후 토큰만 추출
        }
        return null;
    }
}
