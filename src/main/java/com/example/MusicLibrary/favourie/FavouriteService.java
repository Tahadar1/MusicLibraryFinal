package com.example.MusicLibrary.favourie;

import com.example.MusicLibrary.music.Music;
import com.example.MusicLibrary.music.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository, MusicRepository musicRepository) {
        this.favouriteRepository = favouriteRepository;
        this.musicRepository = musicRepository;
    }

    public void addToFavourite(Long id) {
        Optional<Music> music = musicRepository.findById(id);
        if (music.isEmpty()) {
            throw new IllegalStateException("Cannot add to favourite because song does not exists");
        }
        else if (music.get().getIsFavorite()){
            throw new IllegalStateException("Cannot add to favourite because song is already in the playlist");
        }
        else {
            music.get().setIsFavorite(true);
            FavouriteSong favouriteSong = new FavouriteSong(music.get().getFileName(), music.stream().toList());
            favouriteRepository.save(favouriteSong);
        }
    }

    public Stream<FavouriteSong> getAllFavouriteSong(){
        return favouriteRepository.findAll().stream();
    }

    public void removeFromFavourite(Long id){
        boolean exists = favouriteRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Song is not present in favourite list");
        }
        favouriteRepository.deleteById(id);
    }

    public FavouriteSong getSongByFilename(String filename) {
        return favouriteRepository.findByFileName(filename).orElseThrow(() -> new IllegalStateException("Song is not present in the playlist"));
    }
}
