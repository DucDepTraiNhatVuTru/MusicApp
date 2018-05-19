package com.example.dell.music.DataModel;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int PlaylistID;
    private String Name;
    private String UserCreate;
    private int soLuongBaihat;

    public Playlist() {
    }

    public Playlist(int playlistID, String name, String userCreate, int soLuongBaihat) {
        PlaylistID = playlistID;
        Name = name;
        UserCreate = userCreate;
        this.soLuongBaihat = soLuongBaihat;
    }

    public int getPlaylistID() {
        return PlaylistID;
    }

    public void setPlaylistID(int playlistID) {
        PlaylistID = playlistID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserCreate() {
        return UserCreate;
    }

    public void setUserCreate(String userCreate) {
        UserCreate = userCreate;
    }

    public int getSoLuongBaihat() {
        return soLuongBaihat;
    }

    public void setSoLuongBaihat(int soLuongBaihat) {
        this.soLuongBaihat = soLuongBaihat;
    }
}
