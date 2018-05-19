package com.example.dell.music.DoApi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.dell.music.Activity.InfoActivity;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class DoPostRate extends AsyncTask<String, Integer, String> {

    private Activity context;
    private android.support.v7.app.AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    int thisSong;

    public DoPostRate(Activity context, Dialog dialog){
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
        progressDialog.setMessage("Đánh giá thành công!");
        progressDialog.cancel();
        dialog.cancel();

        new DoGetRate(context).execute(Host.LAN_MY_PHONE+"/api/Rates?songID="+thisSong);
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];
        thisSong = Integer.parseInt(strings[1]);
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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Song", Integer.parseInt(strings[1]));
            jsonObject.put("UserRate", strings[2]);
            jsonObject.put("Rate1", Integer.parseInt((strings[3])));
            writer.write(getPostDataString(jsonObject));
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPostDataString(JSONObject params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {
            String key = itr.next();
            try {
                Object value = params.get(key);
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
}
