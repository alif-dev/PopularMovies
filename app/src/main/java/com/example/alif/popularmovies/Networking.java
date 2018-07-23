package com.example.alif.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by alif on 02/03/18.
 */

public class Networking {
    // Default: http://api.themoviedb.org/3/movie/popular
    // Alternative: http://api.themoviedb.org/3/discover/movie?sort_by=...&api_key=...

    public Networking() {

    }

    public static URL buildURL(String sortBy) {
        Uri uri = Uri.parse("http://api.themoviedb.org/3/movie/" + sortBy).buildUpon()
                .appendQueryParameter("api_key", "13781a22a1b2140624ab4e366bec9eb8") // Use your own API key
                .build();
        Log.v("uri = ", uri.toString());
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else
                return null;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
