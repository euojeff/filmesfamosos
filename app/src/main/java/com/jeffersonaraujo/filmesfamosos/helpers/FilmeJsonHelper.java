package com.jeffersonaraujo.filmesfamosos.helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by jeff on 06/12/2017.
 */

public class FilmeJsonHelper{

    private JSONObject mJson;

    private static String IMAGE_ROOT = "http://image.tmdb.org/t/p/";
    private static String TAMANHO_MINIATURA = "w185/";
    private static String TAMANHO_GRANDE = "w780/";

    public FilmeJsonHelper(String json) throws JSONException {
        this.mJson = new JSONObject(json);
    }

    public String getPathCartaz() throws JSONException {
        return IMAGE_ROOT + TAMANHO_MINIATURA + mJson.getString("poster_path");
    }

    public String getBackDrop() throws JSONException {
        return IMAGE_ROOT + TAMANHO_GRANDE + mJson.getString("backdrop_path");
    }

    public String getTitulo() throws JSONException {
        return mJson.getString("title");
    }

    public String getTituloOriginal() throws JSONException {
        return mJson.getString("original_title");
    }

    public String getAvaliacao() throws JSONException {
        return mJson.getString("vote_average");
    }

    public String getDataLancamento() throws JSONException {
        return mJson.getString("release_date");
    }

    public String getSinopse() throws JSONException {
        return mJson.getString("overview");
    }
}
