package com.example.dell.music;

import com.example.dell.music.DataModel.Playlist;
import com.example.dell.music.DataModel.Song;
import com.example.dell.music.DataModel.UserAccount;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Instance {
    public static UserAccount userAccount;
    public static List<Song> songs = new ArrayList<>();
    public static int playingSong = -1;
    public static List<Playlist> playlists = new ArrayList<>();
    public static List<Song> songInPLaylist = new ArrayList<>();
    public static int playingPlaylist = -1;
}
