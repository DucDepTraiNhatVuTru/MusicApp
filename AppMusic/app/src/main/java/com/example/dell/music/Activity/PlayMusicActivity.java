package com.example.dell.music.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dell.music.DataModel.Song;
import com.example.dell.music.DoApi.DoPostSongToPlayList;
import com.example.dell.music.Fragment.HomeFragment;
import com.example.dell.music.Host;
import com.example.dell.music.Instance;
import com.example.dell.music.MyAdapter.ListPLaylistAdapter;
import com.example.dell.music.R;
import com.example.dell.music.Util.MusicPlayer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayMusicActivity extends AppCompatActivity {
    SeekBar seekBar;
    private TextView txtvSingger,txtvSong, txtvTotalTime, txtvCurrentTime;
    private ImageView imageViewPauseOrPlay, imageSong,imgNext, imgBack;
    MediaPlayer mediaPlayer = null;
    Handler handler;
    Runnable runnable;
    int thisSong;
    private Song currentSong;
    private boolean isPlaying = true;
    public static final String BUNDLE ="bundle";
    Dialog dialog;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_play_song,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_info:{
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SongID", thisSong);
                intent.putExtra(BUNDLE,bundle);
                startActivity(intent);
                break;
            }
            case R.id.item_add_to_playlist:{
                dialog = new Dialog(PlayMusicActivity.this);
                dialog.setContentView(R.layout.layout_dialog_my_playlist);
                ListView lv = dialog.findViewById(R.id.lvDialogListPlaylist);
                ListPLaylistAdapter listPLaylistAdapter = new ListPLaylistAdapter(getApplicationContext(),R.layout.layout_item_in_list_music,Instance.playlists);
                lv.setAdapter(listPLaylistAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        new DoPostSongToPlayList(PlayMusicActivity.this,dialog).execute(Host.LAN_MY_PHONE+"api/PlayLists?playlistID="
                                +Instance.playlists.get(position).getPlaylistID()+"&songID="+thisSong);
                    }
                });
                dialog.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        anhXa();
        final Intent intent= getIntent();
        final Bundle bundle = intent.getBundleExtra(HomeFragment.BUNDLE);
        thisSong = bundle.getInt("SongID");
        handler = new Handler();
        currentSong = getSong(thisSong,Instance.songs);
        update();
        if(Instance.playingSong!=thisSong) {

            //mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(currentSong.getLink()));
            mediaPlayer = MusicPlayer.getPlayerInstace(getApplicationContext(), currentSong.getLink());

            Instance.playingSong = thisSong;
        }else if(Instance.playingSong==thisSong){
            mediaPlayer = MusicPlayer.getPlayerIntance();
            seekBar.setMax(mediaPlayer.getDuration());
            playCircle();
            mediaPlayer.start();
            txtvTotalTime.setText(setTime(mediaPlayer.getDuration()));
        }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    playCircle();
                    mediaPlayer.start();
                    txtvTotalTime.setText(setTime(mediaPlayer.getDuration()));
                }
            });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(Instance.playingPlaylist!=-1){
                    for(int i =0; i<Instance.songInPLaylist.size(); i++){
                        if(Instance.songInPLaylist.get(i).getSongID()==thisSong){
                            int next = i +1 ;
                            if(next>=Instance.songInPLaylist.size()){
                                next = 0;
                            }
                            Intent intent1 = new Intent(getApplicationContext(),PlayMusicActivity.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("SongID",Instance.songInPLaylist.get(next).getSongID());
                            intent1.putExtra("bundle",bundle1);
                            startActivity(intent1);
                            finish();
                        }
                    }
                }
            }
        });
            imageViewPauseOrPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPlaying) {
                        imageViewPauseOrPlay.setImageResource(R.drawable.play);
                        isPlaying = false;
                        setMediaPause();
                    } else if (!isPlaying) {
                        imageViewPauseOrPlay.setImageResource(R.drawable.pause);
                        isPlaying = true;
                        setMediaResume();
                        playCircle();
                    }
                }
            });


            imgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Instance.playingPlaylist!=-1){
                        for(int i =0; i<Instance.songInPLaylist.size(); i++){
                            if(Instance.songInPLaylist.get(i).getSongID()==thisSong){
                                int next = i +1 ;
                                if(next>=Instance.songInPLaylist.size()){
                                    next = 0;
                                }
                                Intent intent1 = new Intent(getApplicationContext(),PlayMusicActivity.class);
                                Bundle bundle1 = new Bundle();
                                bundle1.putInt("SongID",Instance.songInPLaylist.get(next).getSongID());
                                intent1.putExtra("bundle",bundle1);
                                startActivity(intent1);
                                finish();
                            }
                        }
                    }
                }
            });

            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Instance.playingPlaylist!=-1){
                        for(int i =0; i<Instance.songInPLaylist.size(); i++){
                            if(Instance.songInPLaylist.get(i).getSongID()==thisSong){
                                int back  = i - 1 ;
                                if(back<0){
                                    back = Instance.songInPLaylist.size()-1;
                                }
                                Intent intent1 = new Intent(getApplicationContext(),PlayMusicActivity.class);
                                Bundle bundle1 = new Bundle();
                                bundle1.putInt("SongID",Instance.songInPLaylist.get(back).getSongID());
                                intent1.putExtra("bundle",bundle1);
                                startActivity(intent1);
                                finish();
                            }
                        }
                    }
                }
            });
    }

    public void playCircle(){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCircle();
                    txtvCurrentTime.setText(setTime(mediaPlayer.getCurrentPosition()));
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }

    private void update(){
        txtvSingger.setText(currentSong.getSinger());
        txtvSong.setText(currentSong.getName());
        Picasso.with(getApplicationContext()).load(currentSong.getLinkImage()).placeholder(R.drawable.music).into(imageSong);
        imageViewPauseOrPlay.setImageResource(R.drawable.pause);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

   /* @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }*/


    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }*/

    private void anhXa(){
        seekBar = findViewById(R.id.song_playing_progressbar);
        txtvSong = findViewById(R.id.txtvNameSongInPlayMusic);
        txtvSingger = findViewById(R.id.txtvSingerInPlayMusic);
        txtvTotalTime = findViewById(R.id.total_time_txt);
        imageSong = findViewById(R.id.imgSongImage);
        txtvCurrentTime = findViewById(R.id.current_time_txt);
        imageViewPauseOrPlay = findViewById(R.id.imgPlayOrPause);
        imgNext = findViewById(R.id.imgNextTrack);
        imgBack = findViewById(R.id.imgRewind);
    }

    private Song getSong(int songID, List<Song> songs){
        for (Song song: songs
             ) {
            if(song.getSongID()==songID){
                return song;
            }
        }
        return new Song();
    }

    private String setTime(int milisecond){
        int seconds = milisecond/1000;
        int minute = seconds/60;
        int second = seconds%60;
        return minute+":"+second;
    }

    private void  setMediaPause(){
        mediaPlayer.pause();
    }

    private void setMediaResume(){
        mediaPlayer.start();
    }
}
