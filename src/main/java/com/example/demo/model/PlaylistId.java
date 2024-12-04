package com.example.demo.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlaylistId implements Serializable {
    private Long playlistId; // Playlist 자체의 ID
    private Long userId;     // User의 ID
    private Long videoId;    // Video의 ID

    // 기본 생성자
    public PlaylistId() {}

    // 생성자
    public PlaylistId(Long playlistId, Long userId, Long videoId) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.videoId = videoId;
    }

    // Getter/Setter
    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    // equals()와 hashCode() 구현 (필수)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistId that = (PlaylistId) o;
        return Objects.equals(playlistId, that.playlistId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(videoId, that.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, userId, videoId);
    }
}
