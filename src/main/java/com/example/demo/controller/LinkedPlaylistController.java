package com.example.demo.controller;

import com.example.demo.model.LinkedPlaylist;
import com.example.demo.service.LinkedPlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // 모든 도메인에서의 요청 허용
public class LinkedPlaylistController {
    private static final Logger logger = LoggerFactory.getLogger(LinkedPlaylistController.class);

    @Autowired
    private LinkedPlaylistService linkedPlaylistService;

    @PostMapping("/addPlaylist")
    public ResponseEntity<Map<String, String>> register(@RequestParam("videoId") String videoId, @RequestParam("playlistId") String playlistId) {
        Map<String, String> response = new HashMap<>();

        Long _videoId = Long.parseLong(videoId); // videoId를 Long으로 변환
        Long _playlistId = Long.parseLong(playlistId); // playlistId를 Long으로 변환

        if (linkedPlaylistService.DuplicateVideo(_videoId, _playlistId)) {
            response.put("message", "playlist에 해당 video가 이미 존재합니다.");
            return ResponseEntity.status(400).body(response); // 400 bad request
        }

        linkedPlaylistService.addVideoToPlaylist(_videoId, _playlistId);

        response.put("message", "playlist에 video 추가 성공");
        return ResponseEntity.ok(response); // JSON 응답
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<List<LinkedPlaylist>> getPlaylistForService(@PathVariable Long playlistId){
        Map<String, String> response = new HashMap<>();

        List<LinkedPlaylist> linkedPlaylists = linkedPlaylistService.getLinkedPlaylistsByPlaylistId(playlistId);

        return ResponseEntity.ok(linkedPlaylists);
    }
}
