package com.snapnote.service;

import com.snapnote.domain.Memo;
import com.snapnote.dto.memo.PopularMemoDto;
import com.snapnote.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopularMemoService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MemoRepository memoRepository;

    private static final String POPULAR_MEMOS_KEY = "popular:memos";

    public List<PopularMemoDto> getPopularMemos() {
        List<PopularMemoDto> cached = (List<PopularMemoDto>) redisTemplate.opsForValue().get(POPULAR_MEMOS_KEY);
        if (cached != null) return cached;

        List<Memo> topMemos = memoRepository.findTop5ByDeletedFalseOrderByViewCountDesc();
        List<PopularMemoDto> result = topMemos.stream()
                .map(PopularMemoDto::from)
                .collect(Collectors.toList());

        redisTemplate.opsForValue().set(POPULAR_MEMOS_KEY, result, Duration.ofMinutes(10));
        return result;
    }
}
