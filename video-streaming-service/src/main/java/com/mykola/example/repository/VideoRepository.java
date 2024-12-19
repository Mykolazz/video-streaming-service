package com.mykola.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mykola.example.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Modifying
    @Query("UPDATE Video v SET v.active = :active WHERE v.id = :id")
    void setActive(Long id, boolean active);

    List<Video> findByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCase(String title, String director);

    List<Video> findByDirectorContainingIgnoreCase(String director);

    List<Video> findByTitleContainingIgnoreCase(String title);
    List<Video> findByActiveTrue();
}
