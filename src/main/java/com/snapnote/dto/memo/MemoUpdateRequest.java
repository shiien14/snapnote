package com.snapnote.dto.memo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemoUpdateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Long folderId; // 변경하고자 하는 폴더 ID (nullable)
}
