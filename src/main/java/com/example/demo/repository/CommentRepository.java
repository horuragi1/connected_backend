package com.example.demo.repository;

import com.example.demo.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByUserId(Long userId);
    List<Comment> findByVideo_IdOrderByCreatedAtAsc(Long videoId);
    Page<Comment> findByVideo_Id(Long videoId, Pageable pageable);
}
