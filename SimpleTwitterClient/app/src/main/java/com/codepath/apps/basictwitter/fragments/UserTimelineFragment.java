package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by khayden on 6/26/14.
 */
public class UserTimelineFragment extends TweetsListFragment {
    User user;
    ArrayList<Tweet> pTweets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bTweets = getArguments();
        pTweets = (ArrayList<Tweet>) bTweets.getSerializable("pTweets");
        addAll(pTweets);

    }

    @Override
    public void populateTimeline(long max_id){
    }
}
