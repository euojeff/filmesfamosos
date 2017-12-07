package com.jeffersonaraujo.filmesfamosos.helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by jeff on 06/12/2017.
 */

public class FilmeJsonHelper{

    private JSONObject mJson;

    private static String IMAGE_ROOT = "http://image.tmdb.org/t/p/w185/";

    public FilmeJsonHelper(String json) throws JSONException {
        this.mJson = new JSONObject(json);
    }

    public String getPathCartaz() throws JSONException {
        return IMAGE_ROOT + mJson.getString("poster_path");
    }
}
