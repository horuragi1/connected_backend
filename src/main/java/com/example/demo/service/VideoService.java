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
    
    public String findTitleById(Long id) {
    	return videoRepository.findTitleById(id);
    }
    
    public Video registerVideo(String title, Long id) {
    	
        Video video = new Video();
        video.setId(id);
        video.setTitle(title);
        video.setDescription(null);
        
        // save 메서드를 사용하여 비디오 등록
        return videoRepository.save(video);
    }
    
    public Long findMaxId() {
    	
    	Long rtr = videoRepository.findMaxId();
    	
    	return rtr;
    	
    }

    public String findDescriptionById(Long id){
        return videoRepository.findDescription(id);
    }
    
    /*public Video saveVideo(Video video) {
        return videoRepository.save(video);  // insert 처리
    }*/
    
}