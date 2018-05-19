package com.example.dell.music.DoApi;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.dell.music.Host;
import com.example.dell.music.Instance;

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

public class DoPostPlayList extends AsyncTask<String,Integer, String> {

    private Activity context;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private ListView listView;

    public DoPostPlayList(Activity context, Dialog dialog, ListView listView){
        this.context=context;
        this.dialog=dialog;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.setTitle("Tạo thành công");
        dialog.cancel();
        progressDialog.cancel();
        new DoGetPlayList(context,listView).execute(Host.LAN_MY_PHONE+"api/PLayLists?user="+ Instance.userAccount.getEmail());
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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name", strings[1]);
            jsonObject.put("UserCreate", strings[2]);
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
