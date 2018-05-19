package com.example.dell.music.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadImage extends AsyncTask<String,Void,Bitmap> {
    URL url = null;
    InputStream inputStream = null;
    Bitmap bitmap = null;
    int response = -1;

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            if (connection instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                response = httpURLConnection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
