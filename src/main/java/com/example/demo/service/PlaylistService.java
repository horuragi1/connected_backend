package com.example.demo.service;

import com.example.demo.model.Playlist;
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
        Optional<Playlist> existingPlaylist = playlistRepository.findByPlaylistIdAndVideoId(playlistId, videoId);

        // 해당 Playlist가 존재하면 중복이므로 true 반환
        return existingPlaylist.isPresent();
    }

    public Playlist createPlaylist(Long videoId, Long userId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        // 가장 큰 playlistId를 찾아서 1을 더한 값을 사용
        Long maxPlaylistId = playlistRepository.findMaxPlaylistId();
        Long newPlaylistId = (maxPlaylistId != null ? maxPlaylistId : 0) + 1;

        Playlist playlist = new Playlist();
        playlist.setPlaylistId(newPlaylistId);  // 새 playlistId 설정
        playlist.setUser(user);
        playlist.setVideo(video);

        return playlistRepository.save(playlist);
    }

    public Playlist addVideoToPlaylist(Long userId, Long videoId, Long playlistId){
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        Playlist playlist = new Playlist();
        playlist.setPlaylistId(playlistId);
        playlist.setUser(user);
        playlist.setVideo(video);

        return playlistRepository.save(playlist);
    }

    public List<Playlist> getPlaylistsByUserAndVideoSorted(Long playlistId) {
        return playlistRepository.findAllByPlaylistIdOrderByPlaylistIdAsc(playlistId);
    }
}