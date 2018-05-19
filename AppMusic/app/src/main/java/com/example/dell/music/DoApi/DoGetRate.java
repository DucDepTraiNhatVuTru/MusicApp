package com.example.dell.music.DoApi;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

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

public class DoGetRate extends AsyncTask<String, Integer, Integer> {
    float rate = 0;
    private Activity context;

    public DoGetRate(Activity context){
        this.context = context;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        //TextView txtvRate = context.findViewById(R.id.txtvRateInInfo);

        //txtvRate.setText(rate +"/10");

        RatingBar rtb = context.findViewById(R.id.rtbRate);
        rtb.setRating(rate);
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
            rate = Float.parseFloat(result);

            Log.d("test",String.valueOf(rate));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
