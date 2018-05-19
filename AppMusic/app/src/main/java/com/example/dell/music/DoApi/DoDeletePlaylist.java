package com.example.dell.music.DoApi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.animation.AccelerateInterpolator;
import android.widget.ListView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DoDeletePlaylist extends AsyncTask<String,Void,Void> {
    private ProgressDialog progressDialog;
    private Activity context;
    private ListView listView;
    public DoDeletePlaylist(Activity context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.cancel();
        context.finish();
    }

    @Override
    protected Void doInBackground(String... strings) {
        String urlString = strings[0];
        URL url=null;
        HttpURLConnection httpURLConnection ;
        InputStream inputStream =null;
        OutputStream outputStream = null;
        String result="" ;
        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            result = reader.readLine();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private void showProgressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
}
