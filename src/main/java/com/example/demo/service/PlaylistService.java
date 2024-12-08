package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Playlist;
import com.example.demo.model.User;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Playlist> getPlaylistForIdSorted(Long userId, int page, int size){
        System.out.println("Fetching page: " + page + " with size: " + size);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("id")));

        // userId에 해당하는 playlist를 페이지네이션과 함께 조회
        return playlistRepository.findByUser_Id(userId, pageable);
    }
}