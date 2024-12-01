/*
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

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean DuplicateVideo(Long userId, Long videoId, Long playlistId) {
        PlaylistKey key = new PlaylistKey(playlistId, userId, videoId);
        return playlistRepository.existsById(key);
    }

    public Playlist createPlaylist(Long videoId, Long userId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        PlaylistKey key = new PlaylistKey(playlistId, userId, videoId);

        Playlist playlist = new Playlist();
        playlist.setId(key);
        playlist.setUser(user);
        playlist.setVideo(video);

        return playlistRepository.save(playlist);
    }

    public Playlist addVideoToPlaylist(Long videoId, Long userId, Long playlistId){
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        PlaylistKey key = new PlaylistKey(playlistId, userId, videoId);

        Playlist playlist = new Playlist();
        playlist.setId(key);
        playlist.setUser(user);
        playlist.setVideo(video);

        return playlistRepository.save(playlist);
    }

    public List<Playlist> getPlaylistsByUserAndVideoSorted(Long playlistId) {
        return playlistRepository.findAllByIdOrderByIdAsc(playlistId);
    }
}
 */