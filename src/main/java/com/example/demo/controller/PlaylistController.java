/*
package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.service.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // 모든 도메인에서의 요청 허용
public class PlaylistController {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);

    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/makePlaylist")
    public ResponseEntity<Map<String, String>> register(@RequestParam("userId") Long userId, @RequestParam("videoId") Long videoId) {
        Map<String, String> response = new HashMap<>();

        playlistService.createPlaylist(videoId, userId);

        response.put("message", "playlist 생성 성공");
        return ResponseEntity.ok(response); // JSON 응답
    }

    @PostMapping("/addPlaylist")
    public ResponseEntity<Map<String, String>> register(@RequestParam("userId") Long userId, @RequestParam("videoId") Long videoId, @RequestParam("playlistId") Long playlistId) {
        Map<String, String> response = new HashMap<>();

        if (playlistService.DuplicateVideo(userId, videoId, playlistId)) {
            response.put("message", "playlist에 해당 video가 이미 존재합니다.");
            return ResponseEntity.status(400).body(response); // 400 bad request
        }

        playlistService.addVideoToPlaylist(videoId, userId, playlistId);

        response.put("message", "playlist에 video 추가 성공");
        return ResponseEntity.ok(response); // JSON 응답
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<List<Playlist>> getPlaylistForService(@PathVariable Long Id){
        Map<String, String> response = new HashMap<>();

        List<Playlist> playlists = playlistService.getPlaylistsByUserAndVideoSorted(Id);

        return ResponseEntity.ok(playlists);
    }
}
 */