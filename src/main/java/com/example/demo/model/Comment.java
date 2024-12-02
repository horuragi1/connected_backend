package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "comments")  // 테이블 이름을 'comments'로 변경
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 댓글 작성자 (User와 관계)

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video; // 댓글이 작성된 비디오 (Video와 관계)

    private LocalDateTime createdAt; // 댓글 작성 시간

    private int likes = 0; // 좋아요 수
    private int dislikes = 0; // 싫어요 수
}
