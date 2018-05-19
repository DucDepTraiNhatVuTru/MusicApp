package com.example.dell.music.MyAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.music.DataModel.Playlist;
import com.example.dell.music.Instance;
import com.example.dell.music.R;

import java.util.List;

public class ListPLaylistAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<Playlist> playlists;

    public ListPLaylistAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.playlists=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(this.context).inflate(this.resource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imgAvatar = convertView.findViewById(R.id.imgImage);
            viewHolder.txtvSongNumber = convertView.findViewById(R.id.txtvSinger);
            viewHolder.txtvName = convertView.findViewById(R.id.txtvNameSong);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = Instance.playlists.get(position);
        viewHolder.imgAvatar.setImageResource(R.drawable.playlist);
        viewHolder.txtvName.setText(playlist.getName());
        if(playlist.getSoLuongBaihat()==0) {
            viewHolder.txtvSongNumber.setText("0");
        }
        viewHolder.txtvSongNumber.setText(String.valueOf(playlist.getSoLuongBaihat()));
        return  convertView;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtvName, txtvSongNumber;
    }
}
