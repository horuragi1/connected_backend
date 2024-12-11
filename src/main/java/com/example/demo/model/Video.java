package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "videos") // 테이블 이름을 'videos'로 변경
public class Video {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;
    //private String Category;
    //private String videoUrl;
    //private Long duration;
}