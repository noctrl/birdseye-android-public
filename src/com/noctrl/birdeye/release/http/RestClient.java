package com.noctrl.birdeye.release.http;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

public class RestClient {

    public static final String LOG_TAG = "RestClient";
    URL url;

    public RestClient(URL url) {
        this.url = url;
    }

    public RestClient(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed url: " + url);
        }
    }

    public Object get() {
        Log.d(LOG_TAG, "In get()");

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection)this.url.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if(statusCode == 200) {
                Log.d(LOG_TAG, "GET Success!");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return new JSONTokener(this.getResponseText(in)).nextValue();
            } else {
                Log.d(LOG_TAG, "Status code: " + statusCode);
            }
        } catch (SocketTimeoutException e) {
            Log.e(LOG_TAG, "Socket timeout on url: " + this.url, e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IO Exception on url: " + this.url, e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Url content body could not be converted to JSON: " + this.url, e);
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return null;
    }

    private String getResponseText(InputStream in) {
        return new Scanner(in).useDelimiter("\\A").next();
    }
}
