package com.example.dell.music.DoApi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dell.music.R;

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

public class DoPostUserAccount extends AsyncTask<String, Integer, String> {

    private Activity context;
    private ProgressDialog progressDialog;

    public DoPostUserAccount(Activity context, ProgressDialog dialog) {
        this.context = context;
        this.progressDialog = dialog;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if(values[0]==200||values[0]==201){
            progressDialog.cancel();
        }
        else {
            progressDialog.cancel();
            TextView txtvCanhBao = context.findViewById(R.id.txtvCanhBao);
            EditText pass = context.findViewById(R.id.edtSignUpPassword);
            EditText confirmPass = context.findViewById(R.id.edtSignUpConfirmPassword);
            txtvCanhBao.setText("email đã có người sử dụng");
            pass.setText("");
            confirmPass.setText("");
        }
        super.onProgressUpdate(values);
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
            jsonObject.put("Email", strings[1]);
            jsonObject.put("Name", strings[2]);
            jsonObject.put("Password", strings[3]);
            jsonObject.put("Type",1);
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
}
