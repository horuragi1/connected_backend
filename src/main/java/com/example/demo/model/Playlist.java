package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "playlist")  // 테이블 이름을 'playlist'로 변경
public class Playlist {
    @Id
    private Long playlistId;  // Primary Key의 일부

    @ManyToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;
}