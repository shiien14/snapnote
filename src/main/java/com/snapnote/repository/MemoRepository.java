package com.snapnote.repository;

import com.snapnote.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByUserEmail(String email);
    List<Memo> findAllByUserEmailAndDeletedFalseOrderByCreatedAtDesc(String email);
    Optional<Memo> findByIdAndUserEmailAndDeletedFalse(Long id, String email);

    @Query("SELECT m FROM Memo m WHERE m.user.email = :email AND m.deleted = false " +
            "AND (LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(m.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:isMath IS NULL OR m.isMath = :isMath) " +
            "ORDER BY m.createdAt DESC")
    List<Memo> searchMemos(@Param("email") String email,
                           @Param("keyword") String keyword,
                           @Param("isMath") Boolean isMath);

}