package com.snapnote.service;

import com.snapnote.domain.Folder;
import com.snapnote.domain.Memo;
import com.snapnote.domain.User;
import com.snapnote.dto.memo.*;
import com.snapnote.repository.FolderRepository;
import com.snapnote.repository.MemoRepository;
import com.snapnote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final OcrService ocrService;

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

        String content = request.getContent();

        // OCR 연동: content가 비어있고 imageUrl이 있을경우
        if ((content == null || content.isBlank()) && request.getImageUrl() != null) {
            String imagePath = Paths.get("uploads", request.getImageUrl().replace("/uploads/", ""))
                    .toAbsolutePath()
                    .toString(); // 예: /Users/시은/dev/snapnote/uploads/xxx.png
            content = ocrService.extractText(imagePath);
        }

        Memo memo = Memo.builder()
                .user(user)
                .title(request.getTitle())
                .content(content != null ? content : "")
                .folder(folder)
                .imageUrl(request.getImageUrl())
                .isMath(false)
                .viewCount(0)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Memo saved = memoRepository.save(memo);
        return new MemoResponse(saved.getId(), "메모 저장 완료");
    }

    public List<MemoSummaryResponse> getUserMemos() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Memo> memos = memoRepository.findAllByUserEmailAndDeletedFalseOrderByCreatedAtDesc(email);

        return memos.stream()
                .map(memo -> new MemoSummaryResponse(
                        memo.getId(),
                        memo.getTitle(),
                        shorten(memo.getContent()),
                        memo.getCreatedAt(),
                        memo.getViewCount()
                ))
                .toList();
    }

    private String shorten(String content) {
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

    public MemoDetailResponse getMemoById(Long id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Memo memo = memoRepository.findByIdAndUserEmailAndDeletedFalse(id, email)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모를 찾을 수 없습니다."));

        memo.setViewCount(memo.getViewCount() + 1); // 조회수 증가
        memoRepository.save(memo); // 변경사항 반영

        return new MemoDetailResponse(
                memo.getId(),
                memo.getTitle(),
                memo.getContent(),
                memo.getIsMath(),
                memo.getViewCount(),
                memo.getCreatedAt()
        );
    }

    public void updateMemo(Long id, MemoUpdateRequest request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Memo memo = memoRepository.findByIdAndUserEmailAndDeletedFalse(id, email)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모를 찾을 수 없습니다."));

        memo.setTitle(request.getTitle());
        memo.setContent(request.getContent());
        memo.setUpdatedAt(LocalDateTime.now());

        if (request.getFolderId() != null) {
            Folder folder = folderRepository.findById(request.getFolderId())
                    .orElseThrow(() -> new IllegalArgumentException("폴더가 존재하지 않습니다."));
            memo.setFolder(folder);
        }

        memoRepository.save(memo);
    }

    public void deleteMemo(Long id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Memo memo = memoRepository.findByIdAndUserEmailAndDeletedFalse(id, email)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모를 찾을 수 없습니다."));

        memo.setDeleted(true);
        memo.setUpdatedAt(LocalDateTime.now());

        memoRepository.save(memo);
    }

}