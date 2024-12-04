package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "videowatched")  // 테이블 이름을 'videowatched'로 변경
public class Videowatched {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 영상 보는 사람 (User와 관계)

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video; // 본 비디오 (Video와 관계)

    private Long watchedTime;
    private LocalDateTime watchedAt;
}