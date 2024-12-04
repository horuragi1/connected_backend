package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Playlist {
    @EmbeddedId
    private PlaylistId id; // 복합 키

    @ManyToOne
    @MapsId("userId") // PlaylistId의 userId와 매핑
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("videoId") // PlaylistId의 videoId와 매핑
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    // 기본 생성자
    public Playlist() {}

    // Getter/Setter
    public PlaylistId getId() {
        return id;
    }

    public void setId(PlaylistId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
