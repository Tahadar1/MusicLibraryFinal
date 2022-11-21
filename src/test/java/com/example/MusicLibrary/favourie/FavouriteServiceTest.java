package com.example.MusicLibrary.favourie;

import com.example.MusicLibrary.music.Music;
import com.example.MusicLibrary.music.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavouriteServiceTest {
    @Mock
    private FavouriteRepository favouriteRepository;

    @Mock
    private MusicRepository musicRepository;
    private FavouriteService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FavouriteService(favouriteRepository, musicRepository);
    }

    @Test //Working Fine
    void willAddToFavourite() {
        FavouriteSong favouriteSong = new FavouriteSong("hello");
        Music music = new Music(favouriteSong.getId(), favouriteSong.getFilename(), false);
        given(musicRepository.findById(favouriteSong.getId())).willReturn(Optional.of(music));

        underTest.addToFavourite(favouriteSong.getId());

        ArgumentCaptor<FavouriteSong> captor = ArgumentCaptor.forClass(FavouriteSong.class);
        verify(favouriteRepository).save(captor.capture());
        FavouriteSong capturedSong = captor.getValue();
        //Assertions.assertThat(capturedSong).isEqualTo(favouriteSong); sending whole music file in service so cannot check with assertions
        assertThat(capturedSong.getFilename()).isEqualTo("hello");
        assertThat(capturedSong.getId()).isEqualTo(null);
    }

    @Test // Working Fine
    void willNotAddToFavouriteAndThrowDoesNotExists(){
        FavouriteSong favouriteSong = new FavouriteSong();

        assertThatThrownBy(() -> underTest.addToFavourite(favouriteSong.getId())).isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot add to favourite because song does not exists");

        verify(favouriteRepository, never()).save(any());
    }

    @Test
    void willNotAddToFavouriteAndThrowAlreadyExists(){
        FavouriteSong favouriteSong = new FavouriteSong("hello");
        Music music = new Music(favouriteSong.getId(), favouriteSong.getFilename(), true);
        given(musicRepository.findById(favouriteSong.getId())).willReturn(Optional.of(music));

        assertThatThrownBy(() -> underTest.addToFavourite(favouriteSong.getId())).isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot add to favourite because song is already in the playlist");

        verify(favouriteRepository, never()).save(any());
    }
    @Test // Working Fine
    void willGetAllFavouriteSong() {
        underTest.getAllFavouriteSong();

        verify(favouriteRepository).findAll();
    }

    @Test // Working Fine
    void willRemoveFromFavourite() {
        FavouriteSong favouriteSong = new FavouriteSong(1L ,"hello");
        given(favouriteRepository.existsById(favouriteSong.getId())).willReturn(true);

        underTest.removeFromFavourite(favouriteSong.getId());

        verify(favouriteRepository).deleteById(favouriteSong.getId());
    }

    @Test // Working Fine
    void willNotRemoveFromFavouriteAndThrow(){
        FavouriteSong favouriteSong = new FavouriteSong(1L ,"hello");

        assertThatThrownBy(() -> underTest.removeFromFavourite(favouriteSong.getId())).isInstanceOf(IllegalStateException.class)
                .hasMessage("Song is not present in favourite list");
    }

    @Test // Working Fine
    void willGetSongByFilename() {
        FavouriteSong favouriteSong = new FavouriteSong(1L ,"hello");
        given(favouriteRepository.findByFileName(favouriteSong.getFilename())).willReturn(Optional.of(favouriteSong));

        underTest.getSongByFilename(favouriteSong.getFilename());

        verify(favouriteRepository).findByFileName(favouriteSong.getFilename());
        assertThat(favouriteSong.getFilename()).isEqualTo("hello");
    }

    @Test // Working Fine
    void willNotGetSongByFilenameAndThrowSongDoesNotExists(){
        FavouriteSong favouriteSong = new FavouriteSong();
        given(favouriteRepository.findByFileName(favouriteSong.getFilename())).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getSongByFilename(favouriteSong.getFilename())).isInstanceOf(IllegalStateException.class)
                .hasMessage("Song is not present in the playlist");

    }
}