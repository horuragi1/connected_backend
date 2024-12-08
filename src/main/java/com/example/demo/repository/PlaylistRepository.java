package com.example.demo.repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findByUser_Id(Long userId, Pageable pageable);
}