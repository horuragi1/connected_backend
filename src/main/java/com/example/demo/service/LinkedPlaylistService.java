package com.example.demo.service;

import com.example.demo.model.LinkedPlaylist;
import com.example.demo.model.Playlist;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.repository.LinkedPlaylistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkedPlaylistService {
    @Autowired
    LinkedPlaylistRepository linkedPlaylistRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    PlaylistRepository playlistRepository;

    public boolean DuplicateVideo(Long videoId, Long playlistId) {
        // 주어진 playlistId와 videoId에 해당하는 LinkedPlaylist가 이미 존재하는지 확인
        Optional<LinkedPlaylist> existingLinkedPlaylist = linkedPlaylistRepository.findByPlaylist_IdAndVideo_Id(playlistId, videoId);

        // Playlist에 해당 Video가 존재하면 중복이므로 true 반환
        return existingLinkedPlaylist.isPresent();
    }

    public LinkedPlaylist addVideoToPlaylist(Long videoId, Long playlistId) {
        // Video 객체를 조회합니다.
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 playlist입니다."));

        LinkedPlaylist linkedPlaylist = new LinkedPlaylist();
        linkedPlaylist.setVideo(video);  // Video 설정
        linkedPlaylist.setPlaylist(playlist); //Playlist 설정

        // 새로 생성된 linkedPlaylist 객체를 저장합니다.
        return linkedPlaylistRepository.save(linkedPlaylist);
    }

    public List<LinkedPlaylist> getLinkedPlaylistsByPlaylistId(Long playlistId) {
        return linkedPlaylistRepository.findByPlaylist_IdOrderByIdAsc(playlistId);
    }
}
