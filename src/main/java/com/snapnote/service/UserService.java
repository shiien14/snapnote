package com.snapnote.service;

import com.snapnote.domain.User;
import com.snapnote.dto.user.MypageResponseDto;
import com.snapnote.dto.user.MypageResponseDto.RecentMemo;
import com.snapnote.repository.MemoRepository;
import com.snapnote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    @Transactional(readOnly = true)
    public MypageResponseDto getMypage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        long totalMemoCount = memoRepository.countByUserAndDeletedFalse(user);
        long mathMemoCount = memoRepository.countByUserAndIsMathTrueAndDeletedFalse(user);

        List<RecentMemo> recentMemos = memoRepository
                .findTop5ByUserAndDeletedFalseOrderByCreatedAtDesc(user).stream()
                .map(m -> new RecentMemo(m.getTitle(), m.getCreatedAt()))
                .toList();

        return MypageResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .totalMemoCount(totalMemoCount)
                .mathMemoCount(mathMemoCount)
                .recentMemos(recentMemos)
                .build();
    }
}
