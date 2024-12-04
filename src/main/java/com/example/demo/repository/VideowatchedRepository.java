package com.example.demo.repository;

import com.example.demo.model.Videowatched;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideowatchedRepository extends JpaRepository<Videowatched, Long> {
    Page<Videowatched> findByUser_Id(Long userId, Pageable pageable);
}
