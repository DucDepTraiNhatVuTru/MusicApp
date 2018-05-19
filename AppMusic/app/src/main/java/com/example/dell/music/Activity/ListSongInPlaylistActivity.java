package com.example.dell.music.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.music.DataModel.Playlist;
import com.example.dell.music.DoApi.DoDeletePlaylist;
import com.example.dell.music.DoApi.DoDeleteSongInPlaylist;
import com.example.dell.music.DoApi.DoGetSongInPlaylist;
import com.example.dell.music.DoApi.DoPutPlaylist;
import com.example.dell.music.Fragment.FragmentPlaylist;
import com.example.dell.music.Host;
import com.example.dell.music.Instance;
import com.example.dell.music.MyAdapter.ListPLaylistAdapter;
import com.example.dell.music.R;

public class ListSongInPlaylistActivity extends AppCompatActivity {
    TextView txtvName, txtvSoLuong;
    ListView lvSongs;
    public static  int thisPlaylist;
    Playlist currentPlaylist;
    public static String BUNDLE ="bundle";
    private AlertDialog alertDialog;
    private Dialog dialog;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_playlist,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_rename_playlist:{
                dialog = new Dialog(ListSongInPlaylistActivity.this);
                dialog.setContentView(R.layout.layout_tao_playlist);
                TextView txtvTitle =dialog.findViewById(R.id.txtvTitile);
                final EditText edtTen = dialog.findViewById(R.id.edtPlaylistName);
                Button btnhuy = dialog.findViewById(R.id.btnCancelTaoPL);
                Button btndoi = dialog.findViewById(R.id.btnTaoPlaylist);
                btndoi.setText("Đổi tên");
                txtvTitle.setText("Đổi tên " +currentPlaylist.getName());
                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btndoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DoPutPlaylist(ListSongInPlaylistActivity.this,dialog).execute(Host.LAN_MY_PHONE+"api/PlayLists/"+currentPlaylist.getPlaylistID(),
                                String.valueOf(currentPlaylist.getPlaylistID()),
                                edtTen.getText().toString(),currentPlaylist.getUserCreate());
                    }
                });
                dialog.show();
                break;
            }
            case R.id.menu_delete_playlist:{
                new DoDeletePlaylist(ListSongInPlaylistActivity.this).execute(Host.LAN_MY_PHONE+"api/PlayLists/"+thisPlaylist);
                Log.d("test",String.valueOf(thisPlaylist));

                Intent intent = new Intent(this,ListMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(FragmentPlaylist.PLAYLISTID,thisPlaylist);
                intent.putExtra(FragmentPlaylist.BUNDLE,bundle);
                startActivity(intent);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song_in_playlist);
        anhxa();
        Intent intent= getIntent();
        Bundle bundle = intent.getBundleExtra(FragmentPlaylist.BUNDLE);
        thisPlaylist = bundle.getInt(FragmentPlaylist.PLAYLISTID);

        currentPlaylist = getPlaylist(thisPlaylist);
        update();
        new DoGetSongInPlaylist(ListSongInPlaylistActivity.this,lvSongs).execute(Host.LAN_MY_PHONE+"api/PLayLists?playlistID="+thisPlaylist);


        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Instance.playingPlaylist = thisPlaylist;
                Intent intent = new Intent(getApplicationContext(), PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SongID", Instance.songInPLaylist.get(position).getSongID());
                intent.putExtra(BUNDLE,bundle);
                startActivity(intent);
            }
        });

        lvSongs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            int pst ;
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pst = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListSongInPlaylistActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa bài " + Instance.songInPLaylist.get(position).getName()+
                        " ra khỏi " + currentPlaylist.getName());
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DoDeleteSongInPlaylist(ListSongInPlaylistActivity.this,alertDialog).execute(Host.LAN_MY_PHONE+
                                "api/PlayLists?playlistID="+thisPlaylist+"&songID="+Instance.songInPLaylist.get(pst).getSongID());
                    }
                });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    private void update(){
        txtvName.setText(currentPlaylist.getName());
        txtvSoLuong.setText(currentPlaylist.getSoLuongBaihat()+" bài hát");
    }

    private void anhxa(){
        txtvName = findViewById(R.id.txtvPLayListName);
        txtvSoLuong = findViewById(R.id.txtvPlayListSongNumber);
        lvSongs = findViewById(R.id.lvListSongAtPlaylist);
    }

    private Playlist getPlaylist(int playlistID){
        for (Playlist playlist: Instance.playlists
             ) {
            if(playlist.getPlaylistID()==playlistID){
                return  playlist;
            }
        }
        return new Playlist();
    }
}
