package com.example.demo.service;

import com.example.demo.model.Playlist;
import com.example.demo.model.PlaylistId;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean DuplicateVideo(Long userId, Long videoId, Long playlistId) {
        // 주어진 playlistId와 videoId에 해당하는 Playlist가 이미 존재하는지 확인
        Optional<Playlist> existingPlaylist = playlistRepository.findById_PlaylistIdAndId_VideoId(playlistId, videoId);

        // 해당 Playlist가 존재하면 중복이므로 true 반환
        return existingPlaylist.isPresent();
    }

    public Playlist createPlaylist(Long videoId, Long userId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        PlaylistId playlistId = new PlaylistId(generateNextPlaylistId(), user.getId(), video.getId());

        // Playlist 객체를 생성하고 값을 설정합니다.
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setVideo(video);
        playlist.setUser(user);

        // Playlist 객체를 저장합니다.
        return playlistRepository.save(playlist);
    }

    private Long generateNextPlaylistId() {
        Long maxPlaylistId = playlistRepository.findMaxPlaylistId();
        return maxPlaylistId + 1;
    }

    public Playlist addVideoToPlaylist(Long userId, Long videoId, Long playlistId) {
        // Video와 User 객체를 조회합니다.
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        PlaylistId playlistIdObj = new PlaylistId(playlistId, userId, videoId);

        // Playlist가 이미 존재하는지 확인
        Optional<Playlist> existingPlaylist = playlistRepository.findById(playlistIdObj);

        if (existingPlaylist.isPresent()) {
            // 이미 존재하는 경우 - 중복 추가를 방지하거나, 필요하면 추가 작업 수행
            throw new IllegalArgumentException("이미 존재하는 플레이리스트 항목입니다.");
        }

        Playlist newPlaylist = new Playlist();
        newPlaylist.setId(playlistIdObj);  // 새 Playlist의 ID 설정
        newPlaylist.setUser(user);  // User 설정
        newPlaylist.setVideo(video);  // Video 설정

        // 새로 생성된 Playlist 객체를 저장합니다.
        return playlistRepository.save(newPlaylist);
    }

    public List<Playlist> getPlaylistsByUserAndVideoSorted(Long playlistId) {
        return playlistRepository.findAllById_PlaylistIdOrderById_PlaylistIdAsc(playlistId);
    }
}