package com.snapnote.dto.memo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoRequest {

    @NotBlank
    private String title;

    private String content;

    private Long folderId; // 선택적으로 폴더 지정

    private String imageUrl; //  이미지 URL 추가
}