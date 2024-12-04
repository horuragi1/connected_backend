package com.example.demo.repository;

import com.example.demo.model.Playlist;
import com.example.demo.model.PlaylistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, PlaylistId> {
    Optional<Playlist> findById_VideoId(Long videoId);
    Optional<Playlist> findById_UserId(Long userId);
    boolean existsById_UserIdAndId_VideoId(Long userId, Long videoId);
    Optional<Playlist> findById_PlaylistIdAndId_VideoId(Long playlistId, Long videoId);
    List<Playlist> findAllById_PlaylistIdOrderById_PlaylistIdAsc(Long playlistId);
    Optional<Playlist> findById(PlaylistId playlistId);

    @Query("SELECT COALESCE(MAX(p.id.playlistId), 0) FROM Playlist p")
    Long findMaxPlaylistId();
}