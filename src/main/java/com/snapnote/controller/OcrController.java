package com.snapnote.controller;

import com.snapnote.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
public class OcrController {

    private final OcrService ocrService;

    @GetMapping
    public ResponseEntity<String> extract(@RequestParam String path) {
        String text = ocrService.extractText(path);
        return ResponseEntity.ok(text);
    }
}
