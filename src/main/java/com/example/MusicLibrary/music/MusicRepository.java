package com.example.MusicLibrary.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

    @Query("SELECT s FROM Music s WHERE s.fileName=?1")
    Optional<Music> findByFileName(String fileName);

    Boolean existsByFileName(String fileName);
}
