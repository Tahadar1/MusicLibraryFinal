package com.example.MusicLibrary.favourie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/music/favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping("/add/{id}")
    public void addToFavouriteSong(@PathVariable("id") Long id) {
        favouriteService.addToFavourite(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UploadFavouriteResponse>> getAllFavouriteSongs(){
        List<UploadFavouriteResponse> favSongs = favouriteService.getAllFavouriteSong().map(favouriteSong -> {
            String url = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("")
                    .path(String.valueOf(favouriteSong.getFilename()))
                    .toUriString();
            return new UploadFavouriteResponse(favouriteSong.getId(), favouriteSong.getFilename(), url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(favSongs);
    }

    @GetMapping("/filename/{filename}")
    public ResponseEntity<UploadFavouriteResponse> getSongFromFavouriteByFilename(@PathVariable("filename") String filename){
        FavouriteSong favouriteSong = favouriteService.getSongByFilename(filename);
        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("")
                .path(String.valueOf(favouriteSong.getFilename()))
                .toUriString();
        UploadFavouriteResponse response = new UploadFavouriteResponse(favouriteSong.getId(), favouriteSong.getFilename(), url);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/id/{id}")
    public void removeFromFavourite(@PathVariable("id") Long id){
        favouriteService.removeFromFavourite(id);
    }
}
