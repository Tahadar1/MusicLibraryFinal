package com.example.MusicLibrary.favourie;

import com.example.MusicLibrary.music.Music;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favourite_song")
public class FavouriteSong {
    @Id
    @SequenceGenerator(name = "favourite_sequence", sequenceName = "favourite_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favourite_sequence")
    private Long Id;

    private String filename;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "has_song",
            joinColumns = @JoinColumn(
                    name = "favourite_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "music_id",
                    referencedColumnName = "music_id"
            )
    )
    private List<Music> music;

    public FavouriteSong(String filename) {
        this.filename = filename;
    }

    public FavouriteSong(Long id, String filename) {
        Id = id;
        this.filename = filename;
    }

    public FavouriteSong(String filename, List<Music> music) {
        this.filename = filename;
        this.music = music;
    }


}
