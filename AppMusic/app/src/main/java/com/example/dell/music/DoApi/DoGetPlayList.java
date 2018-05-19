package com.example.dell.music.DoApi;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.animation.AccelerateInterpolator;
import android.widget.ListView;

import com.example.dell.music.DataModel.Playlist;
import com.example.dell.music.Instance;
import com.example.dell.music.MyAdapter.ListPLaylistAdapter;
import com.example.dell.music.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DoGetPlayList extends AsyncTask<String,Integer,Integer> {

    private Activity context;
    private ListView listView;

    public DoGetPlayList(Activity context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        listView = context.findViewById(R.id.lvListPlaylist);
        ListPLaylistAdapter listPLaylistAdapter = new ListPLaylistAdapter(context,R.layout.layout_item_in_list_music,Instance.playlists);
        listView.setAdapter(listPLaylistAdapter);
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String urlString = strings[0];
        URL url=null;
        HttpURLConnection httpURLConnection ;
        InputStream inputStream =null;
        String result="" ;

        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);

            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            result = reader.readLine();

            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject ;
            publishProgress(jsonArray.length());
            Instance.playlists.clear();
            for (int i=0; i<jsonArray.length(); i++){
                jsonObject=jsonArray.getJSONObject(i);
                Instance.playlists.add(new Playlist(jsonObject.getInt("PLaylistID"),
                        jsonObject.getString( "Name"),jsonObject.getString("UserCreate"),
                        jsonObject.getInt("SongNumbers")));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
