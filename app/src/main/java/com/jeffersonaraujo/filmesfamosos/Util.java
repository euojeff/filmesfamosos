package com.jeffersonaraujo.filmesfamosos;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jeff on 06/12/2017.
 */

public class Util {

    private static String TMDB_API = BuildConfig.TMDB_API_KEY;

    private static String TMDB_POPULAR_BASE_URL = "http://api.themoviedb.org/3/movie/popular";
    private static String API_KEY_PARAM = "api_key";

    public static URL buildPopularMoviesUrl() {
        Uri builtUri = Uri.parse(TMDB_POPULAR_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, TMDB_API)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static int calculaNumeroColunas(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 100);
        return noOfColumns;
    }
}
