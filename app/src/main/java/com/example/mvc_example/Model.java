package com.example.mvc_example;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Model extends AsyncTask<Object, String, String> {
    String reqURL;
    OnResult callback;
    String response;

    public interface OnResult {
        void onSuccess(String result);
        void onError(String error);
    }

    @Override
    protected String doInBackground(Object... objects) {
        reqURL = objects[0].toString();
        callback= (OnResult) objects[1];
        makeServiceCall();
        return null;
    }

    public void makeServiceCall() {

        try {
            URL url = new URL(reqURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
                //presenter.prepareError(e);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //presenter.prepareError(e);
                }
            }
            response = stringBuilder.toString();
            Log.d("JOHN","respuesta : "+response);
            callback.onSuccess(response);
            //presenter.setResponse(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //presenter.prepareError(e);
        } catch (ProtocolException e) {
            e.printStackTrace();
            //presenter.prepareError(e);
        } catch (IOException e) {
            e.printStackTrace();
            //presenter.prepareError(e);
        }
    }



}
