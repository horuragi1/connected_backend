package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.service.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Map<String, String>> register(@RequestParam("userId") String userId, @RequestParam("title") String title) {
        Long _userId = Long.parseLong(userId); // userId를 Long으로 변환
        Map<String, String> response = new HashMap<>();

        playlistService.createPlaylist(_userId, title);

        response.put("message", "playlist 생성 성공");
        return ResponseEntity.ok(response); // JSON 응답
    }

    @GetMapping("/getPlaylist/{userId}")
    public ResponseEntity<PlaylistResponse> getPlaylists(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page, // 기본값 0 (첫 페이지)
            @RequestParam(defaultValue = "10") int size  // 기본값 10 (한 페이지에 10개 항목)
    ){
        Long _userId = Long.parseLong(userId); // userId를 Long으로 변환

        Page<Playlist> playlistPage = playlistService.getPlaylistForIdSorted(_userId, page, size);

        PlaylistResponse response = new PlaylistResponse(
                playlistPage.getTotalElements(),  // 전체 playlist 수
                playlistPage.getNumberOfElements(),  // 현재 페이지의 playlist 수
                playlistPage.getContent()  // playlist 목록 (마지막에 위치)
        );
        logger.info("playlist의 목록 - response: {}", playlistPage);
        return ResponseEntity.ok(response);
    }

    public record PlaylistResponse(
            long totalPlaylists,  // 전체 playlist 수
            int cnt,  // 현재 페이지의 playlist 수
            Object playlists  // playlist 목록
    ) {}
}