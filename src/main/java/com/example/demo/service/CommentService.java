package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment createComment(Long videoId, Long userId, String content) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setVideo(video);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public Comment likeClick (Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Comment입니다."));

        comment.setLikes(comment.getLikes() + 1); // 좋아요 증가

        return commentRepository.save(comment);
    }

    public Comment dislikeClick (Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Comment입니다."));

        comment.setDislikes(comment.getDislikes() + 1); // 좋아요 증가

        return commentRepository.save(comment);
    }

    public Page<Comment> getCommentsForVideoSorted(Long videoId, int page, int size){
        System.out.println("Fetching page: " + page + " with size: " + size);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("createdAt")));

        // videoId에 해당하는 댓글을 페이지네이션과 함께 조회
        return commentRepository.findByVideo_Id(videoId, pageable);
    }
}
