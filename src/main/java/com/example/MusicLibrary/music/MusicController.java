package com.example.MusicLibrary.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "api/v1/music")
public class MusicController {

    private static final Logger logger = LoggerFactory.getLogger(MusicController.class);

    @Autowired
    private MusicService musicService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam(value = "file") MultipartFile uploadFile){
        String message = "";
        try{
            musicService.storeMusic(uploadFile);

            message = "Uploaded file successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UploadResponse>> getAllSongs(){
        List<UploadResponse> songs = musicService.getAllSongs().map(music -> {
            String url = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/Downloads/")
                    .path(String.valueOf(music.getFileName()))
                    .toUriString();
            return new UploadResponse(
                    music.getMusic_Id(),
                    music.getFileName(),
                    url,
                    music.getFileType(),
                    music.getData().length
            );
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(songs);
    }

    @GetMapping("/filename/{fileName}")
    public ResponseEntity<UploadResponse> getMusicByFileName(@PathVariable("fileName") String filename){
        Music music = musicService.getMusicByFileName(filename);
        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/Downloads/")
                .path(String.valueOf(music.getFileName()))
                .toUriString();
        UploadResponse song = new UploadResponse(
                music.getMusic_Id(),
                music.getFileName(),
                url,
                music.getFileType(),
                music.getData().length
        );
        return ResponseEntity.status(HttpStatus.OK).body(song);
    }

    @GetMapping("/id/{music_id}")
    public ResponseEntity<UploadResponse> getMusicById(@PathVariable("music_id") Long id){
        Music music = musicService.getMusicById(id);
        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/Downloads/")
                .path(String.valueOf(music.getFileName()))
                .toUriString();
        UploadResponse song = new UploadResponse(
                music.getMusic_Id(),
                music.getFileName(),
                url,
                music.getFileType(),
                music.getData().length
        );
        return ResponseEntity.status(HttpStatus.OK).body(song);
    }
}
