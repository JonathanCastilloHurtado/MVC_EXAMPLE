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

// Esto model no debería extender directamente del AsynTaks, por lo que te comentaba de que posiblemente sean muchos task a los que hay que consumir
// Ejemplo loginEmail. loginFacebook, etc, etc ...
public class Model extends AsyncTask<Object, String, NetworkResponse> {

	private String reqURL;
	private OnResult callback;

	public interface OnResult {

		void onSuccess(String result);

		void onError(String error);
	}

	/// El model o el Build.url --> Debe tener un path principal (http://cardfindercdmx.com)
	/// y los tasks denen tener parte del path por ejemplo: /personal/get_book.php
	/// y así separar en task diferentes peticiones con su respectiva URL + path.

	public Model() {
		// TODO quitar harcodeo (Se que yo lo puse) pero te quería ejemplificar porqye lo deberías que tener en otro lado por lo que te puse arriba
		this.reqURL = "http://cardfindercdmx.com/personal/get_book.php";
	}

	@Override
	protected NetworkResponse doInBackground(Object... objects) {
		callback = (OnResult) objects[1];
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
			String errorMsg = (response == null) ? "" : getErrorParser(response.getException());
			if (callback != null) {
				callback.onError(errorMsg);
			}
		}
	}

	private String getErrorParser(@NonNull Exception exception) {
		//TODO Los textos de errores no deben de estar hacrdoeados
		// Mételos a un string o decir que el API quizá de proveer esos mensajes de error
		String errorMsg;
		if (exception instanceof FileNotFoundException) {
			errorMsg = "Archivo no encontrado";
		} else {
			errorMsg = "Error no identificado";
		}
		return errorMsg;
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
			return networkResponse;
		} catch (Exception e) {
			networkResponse.setException(e);
			networkResponse.setSuccess(false);
			return networkResponse;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
