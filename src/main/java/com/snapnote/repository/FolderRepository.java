package com.snapnote.repository;

import com.snapnote.domain.Folder;
import com.snapnote.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByUser(User user);
}
