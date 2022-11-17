package com.example.MusicLibrary.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MusicServiceTest {
    @Mock
    private MusicRepository musicRepository;
    private MusicService underTest;

    @BeforeEach
    void setUp(){
        underTest = new MusicService(musicRepository);
    }

    @Test
    @Disabled
    void willStoreMusic() {
        MultipartFile music = new MockMultipartFile("new", "hello", String.valueOf(false), "hello".getBytes());
        underTest.storeMusic(music);

        ArgumentCaptor<Music> captor = ArgumentCaptor.forClass(Music.class);
        verify(musicRepository).save(captor.capture());
        Music capturedMusic = captor.getValue();
        assertThat(capturedMusic).isEqualTo(MockMultipartFile.class);
        //assertThat(capturedMusic.getFileName()).isEqualTo("new");
    }

    @Test
    void willGetMusic() {
        Music music = new Music("GOOD", "mp3", false);
        given(musicRepository.findByFileName(music.getFileName())).willReturn(Optional.of(music));

        underTest.getMusicByFileName(music.getFileName());

        verify(musicRepository).findByFileName(music.getFileName());
    }

    @Test
    void willGetAllSongs(){
        underTest.getAllSongs();

        verify(musicRepository).findAll();
    }
}