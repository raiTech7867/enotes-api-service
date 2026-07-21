package com.raiTech.repository;

import com.raiTech.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

    Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);

    List<Notes> findByCreatedBy(Integer userId);

    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);

    @Query("""
    SELECT n
    FROM Notes n
    WHERE (
        LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(n.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND n.isDeleted = false
    AND n.createdBy = :userId
    """)
    Page<Notes> searchNotes(
            @Param("keyword") String keyword,
            @Param("userId") Integer userId,
            Pageable pageable);
}
