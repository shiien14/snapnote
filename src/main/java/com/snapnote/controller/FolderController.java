package com.snapnote.controller;

import com.snapnote.dto.folder.FolderCreateRequestDto;
import com.snapnote.dto.folder.FolderResponseDto;
import com.snapnote.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderService folderService;

    // 폴더 생성
    @PostMapping
    public ResponseEntity<Void> createFolder(@AuthenticationPrincipal String email,
                                             @RequestBody FolderCreateRequestDto requestDto) {
        folderService.createFolder(email, requestDto);
        return ResponseEntity.ok().build();
    }

    // 목록 조회
    @GetMapping
    public ResponseEntity<List<FolderResponseDto>> getFolders(@AuthenticationPrincipal String email) {
        List<FolderResponseDto> folders = folderService.getFolders(email);
        return ResponseEntity.ok(folders);
    }
}
