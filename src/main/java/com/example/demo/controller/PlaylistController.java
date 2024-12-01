package com.example.demo.controller;

import com.example.demo.service.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlaylistController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/makePlaylist")
    public ResponseEntity<Map<String, String>> register(@RequestParam("userId") Long userId, @RequestParam("videoId") Long videoId) {
        Map<String, String> response = new HashMap<>();

        if (playlistService.DuplicateVideo(userId, videoId)) {
            response.put("message", "중복된 playlist입니다.");
            return ResponseEntity.status(400).body(response); // 400 bad request
        }

        playlistService.createPlaylist(videoId, userId);

        response.put("message", "playlist 생성 성공");
        return ResponseEntity.ok(response); // JSON 응답
    }
}
