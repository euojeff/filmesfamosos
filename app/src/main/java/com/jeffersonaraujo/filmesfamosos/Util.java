package com.jeffersonaraujo.filmesfamosos;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jeff on 06/12/2017.
 */

public class Util {

    private static String TMDB_API = BuildConfig.TMDB_API_KEY;

    private static String TMDB_POPULAR_BASE_URL = "http://api.themoviedb.org/3/movie";
    private static String LISTA_POPULAR_QUERY = "popular";
    private static String LISTA_MELHOR_CLASSIFICADO_QUERY = "top_rated";
    private static String FILME_VIDEOS_QUERY = "videos";
    private static String FILME_REVIEWS_QUERY = "reviews";

    private static String API_KEY_PARAM = "api_key";

    public static URL montarURLMelhorClassificado() {
        return buildMoviesUrl(LISTA_MELHOR_CLASSIFICADO_QUERY, null);
    }

    public static URL montarURLMaisPopular() {
        return buildMoviesUrl(LISTA_POPULAR_QUERY, null);
    }

    public static URL montarURLFilmeReviews(String idFilme){
        return buildMoviesUrl(FILME_REVIEWS_QUERY, idFilme);
    }

    public static URL montarURLFilmeVideos(String idFilme){
        return buildMoviesUrl(FILME_VIDEOS_QUERY, idFilme);
    }

    private static URL buildMoviesUrl(String recursoPath, String idFilme) {

        Uri.Builder builder = Uri.parse(TMDB_POPULAR_BASE_URL).buildUpon();

        if(idFilme != null){
            builder.appendEncodedPath(idFilme);
        }

        builder.appendEncodedPath(recursoPath);
        builder.appendQueryParameter(API_KEY_PARAM, TMDB_API);

        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static int calculaNumeroColunas(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 80);
    }
}
