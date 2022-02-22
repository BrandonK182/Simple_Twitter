package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public long id;
    public String time;
    public String media;
    public int mediaCount;

    public Tweet(){
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        JSONObject entity = jsonObject.getJSONObject("entities");
        JSONArray urls = entity.getJSONArray("urls");

        if(urls.length()>0)
        {
            JSONObject links = urls.getJSONObject(0);
            tweet.media = links.getString("url");
            tweet.mediaCount = 1;
            Log.i("MediaCounter","found");
        }

        tweet.time = tweet.getFormattedTimestamp(tweet.createdAt);
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getFormattedTimestamp(String createdAt)
    {
        return TimeFormatter.getTimeDifference(createdAt);
    }
}
