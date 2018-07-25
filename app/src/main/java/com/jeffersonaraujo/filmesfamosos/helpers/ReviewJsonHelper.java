package com.jeffersonaraujo.filmesfamosos.helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by jeff on 17/07/2018.
 */

public class ReviewJsonHelper implements Serializable {

    private JSONObject mJson;

    public ReviewJsonHelper(String json) throws JSONException {
        this.mJson = new JSONObject(json);
    }

    public String getUser() throws JSONException {
        return mJson.getString("author");
    }

    public String getReview() throws JSONException {
        return mJson.getString("content");
    }
}
