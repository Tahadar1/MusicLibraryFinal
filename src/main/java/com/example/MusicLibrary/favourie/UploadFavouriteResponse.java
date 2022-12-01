package com.example.MusicLibrary.favourie;

public class UploadFavouriteResponse {

    private Long id;

    private Boolean isFavourite;
    private String fileName;
    private String url;

    public UploadFavouriteResponse(Long id, Boolean isFavourite, String fileName, String url) {
        this.id = id;
        this.isFavourite = isFavourite;
        this.fileName = fileName;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
