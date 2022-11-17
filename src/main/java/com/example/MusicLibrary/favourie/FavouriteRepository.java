package com.example.MusicLibrary.favourie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteSong, Long> {

    @Query("SELECT s FROM FavouriteSong s WHERE s.filename=?1")
    Optional<FavouriteSong> findByFileName(String fileName);
}
