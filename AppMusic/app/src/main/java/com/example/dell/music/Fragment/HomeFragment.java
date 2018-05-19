package com.example.dell.music.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dell.music.Activity.ListMusicActivity;
import com.example.dell.music.Activity.PlayMusicActivity;
import com.example.dell.music.DoApi.DoGetSongs;
import com.example.dell.music.Host;
import com.example.dell.music.Instance;
import com.example.dell.music.R;

public class HomeFragment extends android.support.v4.app.Fragment {
    ListView lvSongs;
    public static final String BUNDLE ="bundle";
    public HomeFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        anhXa();
        new DoGetSongs(getActivity(),lvSongs).execute(Host.LAN_MY_PHONE+"api/Songs?soLuong=25");

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Instance.playingPlaylist=-1;
                Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SongID", Instance.songs.get(position).getSongID());
                intent.putExtra(BUNDLE,bundle);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }
    private void anhXa(){
        lvSongs = getActivity().findViewById(R.id.lvListSongAtHome);
    }
}
