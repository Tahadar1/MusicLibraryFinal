package com.example.MusicLibrary.music;

import com.example.MusicLibrary.favourie.FavouriteSong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "music")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Music {
    @Id
    @SequenceGenerator(name = "music_sequence", sequenceName = "music_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "music_sequence")
    private Long music_Id;

    @Column(name = "filename", nullable = false)
    private String fileName;

    @Column(name = "filetype", nullable = false)
    private String fileType;

    private Boolean isFavorite;

    @ManyToMany(mappedBy = "music")
    private List<FavouriteSong> favoriteSongs;

    @Lob
    private byte[] data;

    public Music(String fileName, String fileType, Boolean isFavorite, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.isFavorite = isFavorite;
        this.data = data;
    }

    public Music(String fileName, String fileType, Boolean isFavorite) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.isFavorite = isFavorite;
    }

    public Music(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Music(Long music_Id, String fileName, Boolean isFavorite) {
        this.music_Id = music_Id;
        this.fileName = fileName;
        this.isFavorite = isFavorite;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Music(Long music_Id, String fileName) {
        this.music_Id = music_Id;
        this.fileName = fileName;
    }
}
