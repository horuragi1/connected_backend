package com.example.demo.repository;

import com.example.demo.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findById(Long id);
    
    @Query("SELECT v.title FROM Video v WHERE v.id = :id")
    String findTitleById(@Param("id") Long id);
    
    @Query("SELECT MAX(v.id) FROM Video v")
    Long findMaxId();

    @Query("SELECT v.description FROM Video v WHERE v.id = :id")
    String findDescription(@Param("id") Long id);
}