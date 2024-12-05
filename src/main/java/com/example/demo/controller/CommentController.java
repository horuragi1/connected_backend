package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.service.CommentService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // 모든 도메인에서의 요청 허용
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @PostMapping("comment/create")
    public ResponseEntity<Map<String, String>> create(@RequestParam String galaxy,
                                                      @RequestParam String userId,
                                                      @RequestParam String content) {
    	
    	logger.info("videoId : {}, userId : {}, content : {}", galaxy, userId, content);

        Long _videoId = Long.parseLong(galaxy);
        Long _userId = Long.parseLong(userId);

        try {
            Comment comment = commentService.createComment(_videoId, _userId, content);

            Map<String, String> response = new HashMap<>();
            response.put("message", "댓글 생성 성공");
            response.put("commentId", String.valueOf(comment.getId()));

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("comment/like")
    public ResponseEntity<Map<String, String>> like(@RequestParam Long commentId) {
        try {
            Comment comment = commentService.likeClick(commentId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "댓글 좋아요 성공");
            response.put("commentId", String.valueOf(comment.getId()));

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("comment/dislike")
    public ResponseEntity<Map<String, String>> dislike(@RequestParam Long commentId) {
        try {
            Comment comment = commentService.dislikeClick(commentId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "댓글 싫어요 성공");
            response.put("commentId", String.valueOf(comment.getId()));

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/comment/{videoId}")
    public ResponseEntity<CommentResponse> getCommentsForVideoSorted(
            @PathVariable String videoId,
            @RequestParam(defaultValue = "0") int page, // 기본값 0 (첫 페이지)
            @RequestParam(defaultValue = "10") int size  // 기본값 10 (한 페이지에 10개 항목)
    ) {
        Long _videoId = Long.parseLong(videoId); // videoId를 Long으로 변환

        Page<Comment> commentsPage = commentService.getCommentsForVideoSorted(_videoId, page, size);

        CommentResponse response = new CommentResponse(
                commentsPage.getTotalElements(),  // 전체 댓글 수
                commentsPage.getNumberOfElements(),  // 현재 페이지의 댓글 수
                commentsPage.getContent()  // 댓글 목록 (마지막에 위치)
        );
        
        logger.info("page is {}, size is {}", page, size);

        return ResponseEntity.ok(response);
    }

    public record CommentResponse(
            long totalComments,  // 전체 댓글 수
            int cnt,  // 현재 페이지의 댓글 수
            Object comments  // 댓글 목록
    ) {}
}
