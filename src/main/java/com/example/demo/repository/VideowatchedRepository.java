package com.example.demo.repository;

import com.example.demo.model.Videowatched;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideowatchedRepository extends JpaRepository<Videowatched, Long> {
    Optional<Videowatched> findByUser_IdAndVideo_Id(Long userId, Long videoId);
    Page<Videowatched> findByUser_Id(Long userId, Pageable pageable);
}
