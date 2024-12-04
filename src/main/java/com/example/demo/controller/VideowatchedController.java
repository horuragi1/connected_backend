package com.example.demo.controller;

import com.example.demo.model.Videowatched;
import com.example.demo.service.VideowatchedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // 모든 도메인에서의 요청 허용
public class VideowatchedController {
    private static final Logger logger = LoggerFactory.getLogger(VideowatchedController.class);

    @Autowired
    private VideowatchedService videowatchedService;

    @PostMapping("/videowatched/record")
    public ResponseEntity<Map<String, String>> record (@RequestParam Long videoId,
                                                       @RequestParam Long userId,
                                                       @RequestParam Long watchedTime){
        try {
            Videowatched videowatched = videowatchedService.record(videoId, userId, watchedTime);

            Map<String, String> response = new HashMap<>();
            response.put("message", "영상 기록 성공");
            response.put("watchedId", String.valueOf(videowatched.getId()));

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/videowatched/{userId}/{videoId}")
    public ResponseEntity<Map<String, Long>> getVideoWatchedTime(
            @PathVariable Long userId,
            @PathVariable Long videoId
    ) {
        Optional<Videowatched> videowatched = videowatchedService.getSpecificVideo(userId, videoId);

        Map<String, Long> response = new HashMap<>();
        response.put("watchedTime", videowatched.get().getWatchedTime());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/videowatched/{userId}")
    public ResponseEntity<VideowatchedController.VideowatchedResponse> getCommentsForVideoSorted(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page, // 기본값 0 (첫 페이지)
            @RequestParam(defaultValue = "10") int size  // 기본값 10 (한 페이지에 10개 항목)
    ) {
        Page<Videowatched> videowatchedPage = videowatchedService.getVideowatched(userId, page, size);

        VideowatchedController.VideowatchedResponse response = new VideowatchedController.VideowatchedResponse(
                videowatchedPage.getTotalElements(),  // 전체 영상 수
                videowatchedPage.getNumberOfElements(),  // 현재 페이지의 영상 수
                videowatchedPage.getContent()  // 영상 목록 (마지막에 위치)
        );

        return ResponseEntity.ok(response);
    }

    public record VideowatchedResponse(
            long totalVideos,  // 전체 영상 수
            int cnt,  // 현재 페이지의 영상 수
            Object videos  // 영상 목록
    ) {}
}
