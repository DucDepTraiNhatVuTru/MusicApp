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

import com.example.dell.music.DataModel.Song;
import com.example.dell.music.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListSongAdapter extends ArrayAdapter<Song> {
    private Context context;
    private int resource;
    private List<Song> songs;

    public ListSongAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        songs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(this.context).inflate(this.resource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imgAvatar = convertView.findViewById(R.id.imgImage);
            viewHolder.txtvSinger = convertView.findViewById(R.id.txtvSinger);
            viewHolder.txtvNameSong = convertView.findViewById(R.id.txtvNameSong);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Song song = songs.get(position);
        Picasso.with(this.context).load(song.getLinkImage()).placeholder(R.drawable.ic_launcher_background).into(viewHolder.imgAvatar);
        viewHolder.txtvNameSong.setText(song.getName());
        viewHolder.txtvSinger.setText(song.getSinger());
        return convertView;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtvNameSong, txtvSinger;
    }
}
