package com.example.mvc_example

import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL

class Model : AsyncTask<Any, String, NetworkResponse>() {

    private lateinit var callback: OnResult

    interface OnResult {
        fun onSuccess(result: String?)
        fun onError(error: Exception?)
    }

    override fun onPostExecute(response: NetworkResponse?) {
        super.onPostExecute(response)
        if (response!!.isSuccess) {
            callback.onSuccess(response.message)
        } else {
            callback.onError(response.exception)
        }
    }

    override fun doInBackground(vararg objects: Any): NetworkResponse? {
        val reqURL = objects[0].toString()
        //A difeencia de JAVA, el callback tiene que hacer la signacion del callback recibido.
        callback = objects[1] as OnResult
        return makeServiceCall(reqURL)
    }

     fun makeServiceCall(reqURL: String?): NetworkResponse? {
        val response = NetworkResponse()
        try {
            val url = URL(reqURL)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            val `in`: InputStream = BufferedInputStream(urlConnection.inputStream)
            val reader = BufferedReader(InputStreamReader(`in`))
            val stringBuilder = StringBuilder()
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append('\n')
                }
            } catch (e: IOException) {
                e.printStackTrace()
                response.isSuccess = false
                response.exception = e
            } finally {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    response.isSuccess = false
                    response.exception = e
                }
            }
            response.isSuccess = true
            response.message = stringBuilder.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            response.isSuccess = false
            response.exception = e
        } catch (e: ProtocolException) {
            e.printStackTrace()
            response.isSuccess = false
            response.exception = e
        } catch (e: IOException) {
            e.printStackTrace()
            response.isSuccess = false
            response.exception = e
        }
        return response
    }
}