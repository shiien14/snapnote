package com.snapnote.controller;

import com.snapnote.dto.memo.*;
import com.snapnote.service.MemoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<MemoSummaryResponse>> getMemos() {
        return ResponseEntity.ok(memoService.getUserMemos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoDetailResponse> getMemo(@PathVariable Long id) {
        MemoDetailResponse response = memoService.getMemoById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMemo(@PathVariable Long id,
                                             @Valid @RequestBody MemoUpdateRequest request) {
        memoService.updateMemo(id, request);
        return ResponseEntity.ok("메모 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return ResponseEntity.ok("메모 삭제 완료");
    }

}