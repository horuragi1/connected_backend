package com.example.demo.service;

import com.example.demo.model.Video;
import com.example.demo.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Optional<Video> getVideoById(Long id){
        return videoRepository.findById(id);
    }
}
