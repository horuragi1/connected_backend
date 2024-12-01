package com.example.demo.repository;

import com.example.demo.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByVideoId(Long videoId);
    Optional<Playlist> findByUserId(Long userId);
}
