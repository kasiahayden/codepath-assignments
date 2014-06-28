package com.codepath.apps.basictwitter.models;

import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by khayden on 6/19/14.
 */
public class Tweet {
    private String body;
    private long tweet_id;
    private String createdAt;
    private String timeSinceCreated;
    private User user;

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract values from json to populate the member variables
        try {
            tweet.body = jsonObject.getString("text");
            tweet.tweet_id = jsonObject.getLong("id");
            //tweet.createdAt = jsonObject.getString("created_at");
            tweet.createdAt = getSimpleDate(jsonObject.getString("created_at"));
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.timeSinceCreated = getRelativeTimeAgo(jsonObject.getString("created_at"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public String getBody() {
        return body;
    }

    public long getTweetId() {
        return tweet_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTimeSinceCreated() { return timeSinceCreated; }

    public User getUser() {
        return user;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }
        return tweets;
    }

    @Override
    public String toString() {
        return getBody() + " - " + getUser().getScreenName();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        String formattedDate = "";
        String[] dateArr;
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateArr = relativeDate.split("\\s+");
        try {
            formattedDate = dateArr[0];
            Log.d("tweet relative1", dateArr[1]);
            if (dateArr[1].contains("second")) {
                Log.d("tweet relative", dateArr[1]);
                formattedDate += "s";
            } else if (dateArr[1].contains("minute")) {
                Log.d("tweet relative", dateArr[1]);
                formattedDate += "m";
            } else if (dateArr[1].contains("hour")) {
                formattedDate += "h";
            } else if (dateArr[1].contains("day")) {
                formattedDate += "d";
            } else if (dateArr[1].contains("week")) {
                formattedDate += "w";
            } else if (dateArr[1].contains("month")) {
                formattedDate += "mo";
            } else if (dateArr[1].contains("year")) {
                formattedDate += "y";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        //return relativeDate;
        return formattedDate;
    }

    public static String getSimpleDate(String rawJsonDate) {

        final String OLD_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        final String NEW_FORMAT = "EEE MMM dd HH:mm yyyy";

        String oldDateString = rawJsonDate;
        String newDateString;
        Date d = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        try {
            d = sdf.parse(oldDateString);
        } catch (ParseException e) {
        e.printStackTrace();
        }

        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);
        return newDateString;
    }

    public void onProfileImageSelected(){
        //Toast.makeText(getContext(), "onProfileImageSelected", Toast.LENGTH_LONG).show();
    }
}
