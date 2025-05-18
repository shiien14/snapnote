package com.snapnote.dto.memo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemoSummaryResponse {
    private Long id;
    private String title;
    private String contentPreview;
    private LocalDateTime createdAt;
    private int viewCount;
}
