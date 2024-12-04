package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.model.Videowatched;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VideoRepository;
import com.example.demo.repository.VideowatchedRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VideowatchedService {
    @Autowired
    private VideowatchedRepository videowatchedRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    public Videowatched record(Long videoId, Long userId, Long watchedTime) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Video입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User입니다."));

        Optional<Videowatched> existingVideowatched = videowatchedRepository.findByUser_IdAndVideo_Id(userId, videoId);

        if (existingVideowatched.isPresent()) {
            Videowatched videowatched = existingVideowatched.get();
            videowatched.setWatchedTime(watchedTime);
            videowatched.setWatchedAt(LocalDateTime.now());
            return videowatchedRepository.save(videowatched);
        } else {
            // 레코드가 없으면 새로운 Videowatched 객체를 생성하여 저장
            Videowatched videowatched = new Videowatched();
            videowatched.setUser(user);
            videowatched.setVideo(video);
            videowatched.setWatchedTime(watchedTime);
            videowatched.setWatchedAt(LocalDateTime.now());

            return videowatchedRepository.save(videowatched);
        }
    }

    @Transactional
    public Optional<Videowatched> getSpecificVideo(Long userId, Long videoId){
        return videowatchedRepository.findByUser_IdAndVideo_Id(userId, videoId);
    }

    @Transactional
    public Page<Videowatched> getVideowatched(Long userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("watchedAt")));

        // userId에 해당하는 영상을 페이지네이션과 함께 조회
        return videowatchedRepository.findByUser_Id(userId, pageable);
    }
}
