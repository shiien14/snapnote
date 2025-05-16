package com.snapnote.controller;

import com.snapnote.dto.memo.MemoRequest;
import com.snapnote.dto.memo.MemoResponse;
import com.snapnote.service.MemoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<MemoResponse> createMemo(@Valid @RequestBody MemoRequest request) {
        MemoResponse response = memoService.createMemo(request);
        return ResponseEntity.ok(response);
    }
}