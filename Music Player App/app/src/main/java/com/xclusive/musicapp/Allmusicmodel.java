package com.xclusive.musicapp;

class Allmusicmodel {
    private String musictitle,musicimage;
    private String artist, album, duration,id;


    public Allmusicmodel(String musictitle, String musicimage, String artist, String album, String duration, String id) {
        this.musictitle = musictitle;
        this.musicimage = musicimage;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.id = id;
    }

    public String getMusictitle() {
        return musictitle;
    }

    public void setMusictitle(String musictitle) {
        this.musictitle = musictitle;
    }

    public String getMusicimage() {
        return musicimage;
    }

    public void setMusicimage(String musicimage) {
        this.musicimage = musicimage;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
