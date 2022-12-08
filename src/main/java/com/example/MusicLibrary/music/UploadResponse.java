package com.example.MusicLibrary.music;

public class UploadResponse {
    private Long id;

    private String fileName;
    private String URL;
    private String fileType;

    private Boolean favourite;
    private int size;

    public UploadResponse(Long id, String fileName, String URL, String fileType, int size, Boolean favourite) {
        this.id = id;
        this.fileName = fileName;
        this.URL = URL;
        this.fileType = fileType;
        this.size = size;
        this.favourite = favourite;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
