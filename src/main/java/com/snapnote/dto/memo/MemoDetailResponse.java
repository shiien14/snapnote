package com.snapnote.dto.memo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemoDetailResponse {
    private Long id;
    private String title;
    private String content;
    private Boolean isMath;
    private int viewCount;
    private LocalDateTime createdAt;
}
