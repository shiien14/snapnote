package com.snapnote.service;

import com.snapnote.domain.User;
import com.snapnote.dto.user.MypageResponseDto;
import com.snapnote.dto.user.MypageResponseDto.RecentMemo;
import com.snapnote.dto.user.UpdateUserRequestDto;
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

    public MypageResponseDto updateUserName(String email, UpdateUserRequestDto request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }

        user.setName(request.getName());
        userRepository.save(user);

        // 아래 부분은 실제 서비스 구현에 맞게 채워주세요.
        long totalMemoCount = 0; // 예: memoRepository.countByUser(user);
        long mathMemoCount = 0;  // 예: memoRepository.countByUserAndIsMath(user, true);
        List<RecentMemo> recentMemos = List.of(); // 예: 최근 메모를 가져오는 로직

        return MypageResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .totalMemoCount(totalMemoCount)
                .mathMemoCount(mathMemoCount)
                .recentMemos(recentMemos)
                .build();
    }
}
