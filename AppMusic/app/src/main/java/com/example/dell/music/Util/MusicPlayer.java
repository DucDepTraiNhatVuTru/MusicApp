package com.example.dell.music.Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MusicPlayer {
    private static MediaPlayer mediaPlayer = null;
    private Context context;
    public static MediaPlayer getPlayerInstace(Context context, String uri){
        if(mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(context, Uri.parse(uri));
        }else if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer=null;
            mediaPlayer = MediaPlayer.create(context, Uri.parse(uri));
        }
        return mediaPlayer;
    }

    public static MediaPlayer getPlayerIntance(){
        return mediaPlayer;
    }

}
