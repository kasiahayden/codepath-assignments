package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class FollowersFragment extends TweetsListFragment {  // TODO change to extend a different fragment e.g. FollowListFragment


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO change to get followers api call:

       /* TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonTweets) {
                addAll(Tweet.fromJSONArray(jsonTweets));
            }
        }, user.getScreenName());*/
    }

    public void populateTimeline(long max_id){
    }
}