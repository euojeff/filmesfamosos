package com.jeffersonaraujo.filmesfamosos.helpers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jeff on 17/07/2018.
 */

public class TrailerFilmeJsonHelper {

    private JSONObject mJson;

    private static final String URL_YOUTUBE = "http://www.youtube.com/watch?v=%s";
    private static final String URL_YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/0.jpg";

    public TrailerFilmeJsonHelper(String json) throws JSONException {
        this.mJson = new JSONObject(json);
    }

    public String getkey() throws JSONException {
        return mJson.getString("key");
    }

    public String getUrlYoutube() throws JSONException {
        return String.format(URL_YOUTUBE, getkey());
    }

    public String getUrlYoutubeThunbnail() throws JSONException {
        return String.format(URL_YOUTUBE_THUMBNAIL, getkey());
    }
}
