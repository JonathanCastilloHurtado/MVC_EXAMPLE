package com.example.mvc_example.models;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;

import com.example.mvc_example.BuildConfig;

// Esto model no debería extender directamente del AsynTaks, por lo que te comentaba de que posiblemente sean muchos task a los que hay que consumir
// Ejemplo loginEmail. loginFacebook, etc, etc ...
public class Model extends AsyncTask<Object, String, NetworkResponse> {

	private String reqURL;
	private OnResult callback;

	public interface OnResult {
		void onSuccess(String result);
		void onError(Exception error);
	}

	public Model() {
		this.reqURL = BuildConfig.url+ "personal/get_book.php";
	}

	@Override
	protected NetworkResponse doInBackground(Object... objects) {
		callback = (OnResult) objects[0];
		return makeServiceCall();
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

	private NetworkResponse makeServiceCall() {
		InputStream in = null;
		NetworkResponse networkResponse = new NetworkResponse();
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
			networkResponse.setMessage(stringBuilder.toString());
			networkResponse.setSuccess(true);
		} catch (Exception e) {
			networkResponse.setException(e);
			networkResponse.setSuccess(false);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return networkResponse;
	}
}
