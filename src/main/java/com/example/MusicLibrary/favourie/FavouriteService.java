package com.example.MusicLibrary.favourie;

import com.example.MusicLibrary.music.Music;
import com.example.MusicLibrary.music.MusicRepository;
import com.example.MusicLibrary.music.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final MusicRepository musicRepository;

    private final MusicService musicService;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository, MusicRepository musicRepository, MusicService musicService) {
        this.favouriteRepository = favouriteRepository;
        this.musicRepository = musicRepository;
        this.musicService = musicService;
    }

    public void addToFavourite(Long id) {
        Optional<Music> music = musicRepository.findById(id);
        if (music.isEmpty()) {
            throw new IllegalStateException("Cannot add to favourite because song does not exists");
        } else if (music.get().getIsFavorite()) {
            throw new IllegalStateException("Cannot add to favourite because song is already in the playlist");
        } else {
            music.get().setIsFavorite(true);
            FavouriteSong favouriteSong = new FavouriteSong(music.get().getMusic_Id(), music.get().getIsFavorite(), music.get().getFileName());
            favouriteRepository.save(favouriteSong);
        }
    }

    public Stream<FavouriteSong> getAllFavouriteSong() {
        return favouriteRepository.findAll().stream();
    }

    public void removeFromFavourite(Long id) {
        Optional<FavouriteSong> favouriteSong = favouriteRepository.findById(id);
        Optional<Music> music = musicRepository.findById(id);
        if (favouriteSong.isEmpty()) {
            throw new IllegalStateException("Song is not present in favourite list");
        }
        favouriteSong.get().setIsFavourite(false);
        music.get().setIsFavorite(false);
        favouriteRepository.deleteById(id);
    }

    public FavouriteSong getSongByFilename(String filename) {
        return favouriteRepository.findByFileName(filename).orElseThrow(() -> new IllegalStateException("Song is not present in the playlist"));
    }
}
