package com.example.demo.repository;

import com.example.demo.model.Playlist;
import com.example.demo.model.PlaylistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, PlaylistId> {
    Optional<Playlist> findByVideoId(Long videoId);
    Optional<Playlist> findByUserId(Long userId);
    boolean existsByUserIdAndVideoId(Long userId, Long videoId);
    Optional<Playlist> findByPlaylistIdAndVideoId(Long playlistId, Long videoId);
    List<Playlist> findAllByPlaylistIdOrderByPlaylistIdAsc(Long playlistId);
    Optional<Playlist> findById(PlaylistId playlistId);

    @Query("SELECT COALESCE(MAX(p.playlistId), 0) FROM Playlist p")
    Long findMaxPlaylistId();
}