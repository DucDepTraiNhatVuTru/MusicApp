package com.example.dell.music.DataModel;

public class Song {
    private int SongID;
    private String Name;
    private String Singer;
    private String Author;
    private String Kind;
    private String Link;
    private String LinkImage;

    public Song() {
    }

    public Song(int songID, String name, String singer, String author, String kind, String link, String linkImage) {
        SongID = songID;
        Name = name;
        Singer = singer;
        Author = author;
        Kind = kind;
        Link = link;
        LinkImage = linkImage;
    }

    public int getSongID() {
        return SongID;
    }

    public void setSongID(int songID) {
        SongID = songID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String singer) {
        Singer = singer;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getKind() {
        return Kind;
    }

    public void setKind(String kind) {
        Kind = kind;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getLinkImage() {
        return LinkImage;
    }

    public void setLinkImage(String linkImage) {
        LinkImage = linkImage;
    }
}
