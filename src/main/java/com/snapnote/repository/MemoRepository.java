package com.snapnote.repository;

import com.snapnote.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByUserEmail(String email);
    List<Memo> findAllByUserEmailAndDeletedFalseOrderByCreatedAtDesc(String email);
    Optional<Memo> findByIdAndUserEmailAndDeletedFalse(Long id, String email);
}