package com.snapnote.dto.memo;

import com.snapnote.domain.Memo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopularMemoDto {
    private Long id;
    private String title;
    private String contentPreview;
    private LocalDateTime createdAt;
    private Integer viewCount;

    public static PopularMemoDto from(Memo memo) {
        return PopularMemoDto.builder()
                .id(memo.getId())
                .title(memo.getTitle())
                .contentPreview(memo.getContent().length() > 50 ? memo.getContent().substring(0, 50) + "..." : memo.getContent())
                .createdAt(memo.getCreatedAt())
                .viewCount(memo.getViewCount())
                .build();
    }
}
