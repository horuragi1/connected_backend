package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "playlist")  // 테이블 이름을 'playlist'로 변경
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 댓글 작성자 (User와 관계)

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video; // 댓글이 작성된 비디오 (Video와 관계)
}
