package com.example.MusicLibrary.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    //Not Working
    @Test
    @Disabled
    void willStoreMusic() {
        Music music = new Music("new", ".mp3", false, "hello".getBytes());
        given(musicRepository.existsByFileName(music.getFileName())).willReturn(false);

        underTest.storeMusic((MultipartFile) music);

        ArgumentCaptor<Music> captor = ArgumentCaptor.forClass(Music.class);
        verify(musicRepository).save(captor.capture());
        Music capturedMusic = captor.getValue();
        assertThat(capturedMusic).isEqualTo(music);
        assertThat(capturedMusic.getFileName()).isEqualTo("new");
    }

    @Test
    void willGetMusicByFileName() {
        Music music = new Music("GOOD", "mp3", false);
        given(musicRepository.findByFileName(music.getFileName())).willReturn(Optional.of(music));

        underTest.getMusicByFileName(music.getFileName());

        verify(musicRepository).findByFileName(music.getFileName());
    }

    @Test
    void willNotGetMusicByFileNameAndThrowDoesNotExists(){
        Music music = new Music();

        assertThatThrownBy(() -> underTest.getMusicByFileName(music.getFileName())).isInstanceOf(IllegalStateException.class)
                .hasMessage("Song with Name "+music.getFileName()+" does not exist");

        //verify(musicRepository, never()).findByFileName(any());
    }


    @Test
    void willGetMusicById(){
        Music music = new Music("GOOD", "mp3", false);
        given(musicRepository.findById(music.getMusic_Id())).willReturn(Optional.of(music));

        underTest.getMusicById(music.getMusic_Id());

        verify(musicRepository).findById(music.getMusic_Id());
    }

    @Test
    void willNotGetMusicByIdAndThrowDoesNotExists(){
        Music music = new Music();

        assertThatThrownBy(() -> underTest.getMusicById(music.getMusic_Id())).isInstanceOf(IllegalStateException.class)
                .hasMessage("Song with id "+music.getMusic_Id()+" does not exists");

        //verify(musicRepository, never()).findById(any());
    }
    @Test
    void willGetAllSongs(){
        underTest.getAllSongs();

        verify(musicRepository).findAll();
    }
}