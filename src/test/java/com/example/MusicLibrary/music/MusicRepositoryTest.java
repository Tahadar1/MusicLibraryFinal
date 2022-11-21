package com.example.MusicLibrary.music;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MusicRepositoryTest {

    @Autowired
    private MusicRepository underTest;

    @Test
    void willFindMusicByFileName() {
        String filename = "hello";
        Music music = new Music(filename,".mp3", false);

        underTest.save(music);

        Optional<Music> foundMusic = underTest.findByFileName(filename);

        assertThat(foundMusic).isNotEmpty();
        assertThat(foundMusic.get().getFileName()).isEqualTo(filename);
    }

    @Test
    void willNotFindMusicByFileName(){
        String filename = "hello";
        Music music = new Music(filename,".mp3", false);

        Optional<Music> foundMusic = underTest.findByFileName(filename);

        assertThat(foundMusic).isEmpty();
    }

    @Test
    void existsByFileName() {
        String filename = "hello";
        Music music = new Music(filename,".mp3", false);

        underTest.save(music);

        Boolean exists = underTest.existsByFileName(filename);

        assertThat(exists).isEqualTo(true);
    }

    @Test
    void doesNotExistsByFileName() {
        String filename = "hello";
        Music music = new Music(filename,".mp3", false);

        Boolean exists = underTest.existsByFileName(filename);

        assertThat(exists).isEqualTo(false);
    }
}