package com.jeffersonaraujo.filmesfamosos.helpers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jeff on 06/12/2017.
 */

public class FilmeJsonHelper {

    private JSONObject json;

    private static String IMAGE_ROOT = "http://image.tmdb.org/t/p/w185/";

    public FilmeJsonHelper(JSONObject jsonObject){
        this.json = jsonObject;
    }

    public String getTitulo() throws JSONException {
        return json.getString("title");
    }

    public String getPathCartaz() throws JSONException {
        return IMAGE_ROOT + json.getString("poster_path");
    }
}
