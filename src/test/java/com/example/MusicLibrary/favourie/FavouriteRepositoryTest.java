package com.example.MusicLibrary.favourie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FavouriteRepositoryTest {

    @Autowired
    private FavouriteRepository underTest;

    @Test
    void willFindSongByFileName() {
        String fileName = "Hello";
        FavouriteSong favouriteSong = new FavouriteSong(fileName);
        underTest.save(favouriteSong);

        Optional<FavouriteSong> foundSong = underTest.findByFileName(fileName);

        assertThat(foundSong).isNotEmpty();
        assertThat(foundSong.get().getFilename()).isEqualTo(fileName);
    }

    @Test
    void willNotFindSongByFileName(){
        String fileName = "Hello";
        FavouriteSong favouriteSong = new FavouriteSong(fileName);

        Optional<FavouriteSong> foundSong = underTest.findByFileName(fileName);

        assertThat(foundSong).isEmpty();
    }
}