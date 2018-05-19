package com.example.dell.music.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.music.DataModel.Song;
import com.example.dell.music.DoApi.DoGetRate;
import com.example.dell.music.DoApi.DoPostRate;
import com.example.dell.music.Fragment.HomeFragment;
import com.example.dell.music.Host;
import com.example.dell.music.Instance;
import com.example.dell.music.R;

public class InfoActivity extends AppCompatActivity {
    private int thisSong;
    private Song currentSong;
    private TextView txtvName, txtvSinger, txtvAuthor, txtvKind, txtvRate;
    private ImageView imgAddRate;
    private RatingBar rtbRating;
    private AlertDialog alertDialog;
    Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_song);
        anhXa();
        Intent intent= getIntent();
        Bundle bundle = intent.getBundleExtra(PlayMusicActivity.BUNDLE);
        thisSong = bundle.getInt("SongID");
        currentSong = getSong(thisSong);
        setTitle("Thông tin bài hát");
        update();

        new DoGetRate(InfoActivity.this).execute(Host.LAN_MY_PHONE+"/api/Rates?songID="+thisSong);

        imgAddRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

    }

    private void update(){
        txtvName.setText(currentSong.getName());
        txtvSinger.setText(currentSong.getSinger());
        txtvAuthor.setText(currentSong.getAuthor());
        txtvKind.setText(currentSong.getKind());
        rtbRating.setIsIndicator(true);
    }

    private void anhXa(){
        txtvName = findViewById(R.id.txtvNameSongInInfo);
        txtvSinger = findViewById(R.id.txtvSingerInInfo);
        txtvAuthor = findViewById(R.id.txtvAuthorInInfo);
        txtvKind = findViewById(R.id.txtvKindInInfo);
        //txtvRate = findViewById(R.id.txtvRateInInfo);
        imgAddRate = findViewById(R.id.imgAddRate);
        rtbRating = findViewById(R.id.rtbRate);
    }

    private Song getSong(int songID){
        for (Song song: Instance.songs
                ) {
            if(song.getSongID()==songID){
                return song;
            }
        }
        return new Song();
    }

    public void showAlertDialog()
    {
        dialog = new Dialog(InfoActivity.this);
        dialog.setContentView(R.layout.layout_dialog_rate);
        dialog.setTitle("Đánh Giá");
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnRate = dialog.findViewById(R.id.btnRate);
        //final EditText edtPoint = dialog.findViewById(R.id.edtRatePoint);
        final RatingBar ratingBar = dialog.findViewById(R.id.rtbUserRate);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test",String.valueOf(ratingBar.getRating()));
                new DoPostRate(InfoActivity.this,dialog).execute(Host.LAN_MY_PHONE+"api/Rates",String.valueOf(thisSong)
                        ,Instance.userAccount.getEmail(),String.valueOf((int)ratingBar.getRating()));
            }
        });
        dialog.show();
    }
}
