package com.example.demo.repository;

import com.example.demo.model.LinkedPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkedPlaylistRepository extends JpaRepository<LinkedPlaylist, Long> {
    // Playlist의 id와 Video의 id가 모두 일치하는 항목 검색
    Optional<LinkedPlaylist> findByPlaylist_IdAndVideo_Id(Long playlistId, Long videoId);

    // Playlist의 id와 일치하는 LinkedPlaylist를 id 기준으로 정렬
    List<LinkedPlaylist> findByPlaylist_IdOrderByIdAsc(Long playlistId);
}
