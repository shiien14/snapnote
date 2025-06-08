package com.snapnote.service;

import com.snapnote.domain.Folder;
import com.snapnote.domain.User;
import com.snapnote.dto.folder.FolderCreateRequestDto;
import com.snapnote.dto.folder.FolderResponseDto;
import com.snapnote.repository.FolderRepository;
import com.snapnote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createFolder(String email, FolderCreateRequestDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Folder folder = Folder.builder()
                .name(requestDto.getName())
                .user(user)
                .build();
        folderRepository.save(folder);
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> getFolders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return folderRepository.findByUser(user).stream()
                .map(f -> new FolderResponseDto(f.getId(), f.getName()))
                .toList();
    }
}
