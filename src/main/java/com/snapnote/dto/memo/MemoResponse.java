package com.snapnote.dto.memo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemoResponse {
    private Long memoId;
    private String message;
}