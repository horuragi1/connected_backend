package com.example.demo.service;

import com.example.demo.model.Playlist;
import com.example.demo.model.User;
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

    public Playlist createPlaylist(Long userId, String title) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        // Playlist 객체를 생성하고 값을 설정합니다.
        Playlist playlist = new Playlist();
        playlist.setUser(user);
        playlist.setTitle(title);

        // Playlist 객체를 저장합니다.
        return playlistRepository.save(playlist);
    }
}