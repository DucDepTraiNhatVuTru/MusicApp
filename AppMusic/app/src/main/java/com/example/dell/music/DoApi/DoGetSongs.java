package com.example.dell.music.DoApi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.dell.music.MyAdapter.ListSongAdapter;
import com.example.dell.music.DataModel.Song;
import com.example.dell.music.Instance;
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

public class DoGetSongs extends AsyncTask<String,Integer,Integer> {

    private Activity context;
    private ListView listView;
    private ProgressDialog progressDialog;

    public DoGetSongs(Activity context, ListView listView){
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
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
            Instance.songs.clear();
            for (int i=0; i<jsonArray.length(); i++){
                jsonObject=jsonArray.getJSONObject(i);
                Instance.songs.add(new Song(jsonObject.getInt("SongID"),jsonObject.getString("Name"),
                        jsonObject.getString("Singer"),jsonObject.getString("Author"),
                        jsonObject.getString("Kind"),jsonObject.getString("LinkSong"),
                        jsonObject.getString("LinkImage")));
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

    @Override
    protected void onPostExecute(Integer integer) {
        listView = context.findViewById(R.id.lvListSongAtHome);
        com.example.dell.music.MyAdapter.ListSongAdapter listSongAdapter = new ListSongAdapter(context,R.layout.layout_item_in_list_music,Instance.songs);
        listView.setAdapter(listSongAdapter);
        progressDialog.cancel();
    }
    private void showProgressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
}
