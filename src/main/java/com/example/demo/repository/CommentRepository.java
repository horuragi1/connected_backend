package com.example.demo.repository;

import com.example.demo.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByVideoId(Long videoId, Pageable pageable);
    Optional<Comment> findByUserId(Long userId);
    List<Comment> findByVideoIdOrderByCreatedAtAsc(Long videoId);
    Page<Comment> findbyPageVideoId(Long videoId, Pageable pageable);
}
