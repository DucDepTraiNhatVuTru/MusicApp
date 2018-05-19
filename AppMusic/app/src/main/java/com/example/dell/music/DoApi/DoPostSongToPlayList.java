package com.example.dell.music.DoApi;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.dell.music.Fragment.FragmentPlaylist;
import com.example.dell.music.Host;

import org.json.JSONException;
import org.json.JSONObject;

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

import javax.net.ssl.HttpsURLConnection;

public class DoPostSongToPlayList extends AsyncTask<String,Integer,String> {
    private Activity context;
    private Dialog dialog;
    private ProgressDialog progressDialog;

    public DoPostSongToPlayList(Activity context, Dialog dialog){
        this.context = context;
        this.dialog = dialog;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.cancel();
        progressDialog.cancel();
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            writer.write("");
            writer.flush();
            writer.close();
            outputStream.close();
            int responseCode = httpURLConnection.getResponseCode();
            publishProgress(responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

            } else {
                return new String("false : " + responseCode);
            }
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
