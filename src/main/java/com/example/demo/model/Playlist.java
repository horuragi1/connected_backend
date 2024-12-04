package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
@Entity
@Getter @Setter
@Table(name = "playlist")  // 테이블 이름을 'playlist'로 변경
public class Playlist {
    @Embeddable
    public static class PlaylistId implements Serializable {
        private Long playlistId;
        private Long userId;
        private Long videoId;

        // 기본 생성자, equals(), hashCode() 생략
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;  // Primary Key의 일부

    @ManyToOne
    @MapsId("userId")  // 복합 키의 userId와 매핑
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("videoId")  // 복합 키의 videoId와 매핑
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @EmbeddedId
    private PlaylistId id;
}
 */