package com.example.mvc_example.model;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Model extends AsyncTask<Object, String, NetworkResponse> {

    private OnResult callback;

    public interface OnResult {
        void onSuccess(String result);

        void onError(Exception error);
    }

    public Model() {

    }

    @Override
    protected NetworkResponse doInBackground(Object... objects) {
        final String url = objects[0].toString();
        callback = (OnResult) objects[1];
        return makeServiceCall(url);
    }

    @Override
    protected void onPostExecute(NetworkResponse response) {
        super.onPostExecute(response);
        if (response != null && response.isSuccess()) {
            if (callback != null) {
                callback.onSuccess(response.getMessage());
            }
        } else {
            if (callback != null) {
                callback.onError(response.getException());
            }
        }
    }

    private NetworkResponse makeServiceCall(String reqURL) {
        InputStream in = null;
        NetworkResponse response = new NetworkResponse();
        try {
            URL url = new URL(reqURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            response.setSuccess(true);
            response.setMessage(stringBuilder.toString());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                response.setSuccess(false);
                response.setException(e);
            }
        }
        return response;
    }
}
