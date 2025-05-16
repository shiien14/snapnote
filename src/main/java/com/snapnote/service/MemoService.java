package com.snapnote.service;

import com.snapnote.domain.Folder;
import com.snapnote.domain.Memo;
import com.snapnote.domain.User;
import com.snapnote.dto.memo.MemoRequest;
import com.snapnote.dto.memo.MemoResponse;
import com.snapnote.repository.FolderRepository;
import com.snapnote.repository.MemoRepository;
import com.snapnote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;

    public MemoResponse createMemo(MemoRequest request) {
        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        Folder folder = null;
        if (request.getFolderId() != null) {
            folder = folderRepository.findById(request.getFolderId())
                    .orElseThrow(() -> new IllegalArgumentException("폴더가 존재하지 않습니다."));
        }

        Memo memo = Memo.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .folder(folder)
                .isMath(false)
                .viewCount(0)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Memo saved = memoRepository.save(memo);
        return new MemoResponse(saved.getId(), "메모 저장 완료");
    }
}