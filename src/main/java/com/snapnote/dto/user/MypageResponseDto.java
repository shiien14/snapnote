package com.snapnote.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {
    private String email;
    private String name;
    private long totalMemoCount;
    private long mathMemoCount;
    private List<RecentMemo> recentMemos;


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentMemo {
        private String title;
        private LocalDateTime createdAt;
    }
}