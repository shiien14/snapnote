package com.snapnote.controller;

import com.snapnote.dto.memo.PopularMemoDto;
import com.snapnote.service.PopularMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/popular")
public class PopularMemoController {

    private final PopularMemoService popularMemoService;

    @GetMapping
    public ResponseEntity<List<PopularMemoDto>> getPopularMemos() {
        return ResponseEntity.ok(popularMemoService.getPopularMemos());
    }
}
