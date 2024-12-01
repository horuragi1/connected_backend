package com.example.demo.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistId implements Serializable {
    private Long playlistId;
    private User user;
    private Video video;
}