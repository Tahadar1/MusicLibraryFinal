package com.example.MusicLibrary.music;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class MusicService {
    private final MusicRepository musicRepository;

    @Autowired
    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public void storeMusic(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Boolean exists = musicRepository.existsByFileName(fileName);
        if(exists)
        {
            throw new IllegalStateException("Song with name "+fileName+" already exists");
        }
        else {
            try {
                if (fileName.contains("..")) {
                    throw new IllegalStateException("File Name contain invalid path");
                }
                Music music = new Music(fileName, file.getContentType(), false, file.getBytes());
                musicRepository.save(music);
            } catch (IOException ex) {
                throw new IllegalStateException("Could not store File" + fileName);
            }
        }
    }

    public Music getMusicByFileName(String fileName){
        return musicRepository.findByFileName(fileName).orElseThrow(() -> new IllegalStateException("Song with Name "+fileName+" does not exist"));
    }

    public Music getMusicById(Long id) {
        return musicRepository.findById(id).orElseThrow(() -> new IllegalStateException("Song with id "+id + " does not exists"));
    }

    public Stream<Music> getAllSongs() {
        return musicRepository.findAll().stream();
    }
}
