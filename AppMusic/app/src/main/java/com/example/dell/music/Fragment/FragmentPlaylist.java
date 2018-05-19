package com.example.dell.music.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dell.music.Activity.ListSongInPlaylistActivity;
import com.example.dell.music.DoApi.DoGetPlayList;
import com.example.dell.music.DoApi.DoPostPlayList;
import com.example.dell.music.Host;
import com.example.dell.music.Instance;
import com.example.dell.music.R;

public class FragmentPlaylist extends android.support.v4.app.Fragment {
    private FloatingActionButton floatingActionButtonAdd;
    private Context context;
    private Dialog dialog;
    public static ListView lvPlaylist;
    public static String BUNDLE ="BUNDLE";
    public static String PLAYLISTID= "PLAYLISTID";
    public FragmentPlaylist() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        anhXa();
        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        new DoGetPlayList(getActivity(),lvPlaylist).execute(Host.LAN_MY_PHONE+"api/PLayLists?user="+Instance.userAccount.getEmail());

        lvPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ListSongInPlaylistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(PLAYLISTID, Instance.playlists.get(position).getPlaylistID());
                intent.putExtra(BUNDLE,bundle);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_playlist,container,false);
    }

    private void anhXa(){
       floatingActionButtonAdd = (FloatingActionButton) getActivity().findViewById(R.id.floatingActionButtonAdd);
       lvPlaylist = getActivity().findViewById(R.id.lvListPlaylist);
    }

    private void showDialog(){
        dialog = new Dialog(getContext());
        dialog.setTitle("Tạo mới Playlist");
        dialog.setContentView(R.layout.layout_tao_playlist);
        final EditText edtTenPL = dialog.findViewById(R.id.edtPlaylistName);
        Button btnCancel = dialog.findViewById(R.id.btnCancelTaoPL);
        Button btnCreate = dialog.findViewById(R.id.btnTaoPlaylist);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoPostPlayList(getActivity(),dialog,lvPlaylist).execute(Host.LAN_MY_PHONE+"/api/PlayLists",edtTenPL.getText().toString(),"minhduc@gmail.com");
            }
        });
        dialog.show();
    }
}
