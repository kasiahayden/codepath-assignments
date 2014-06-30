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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*String user_name = "" //getIntent().getStringExtra("user_name");
        if (user_name != null) {
            //Toast.makeText(this, "user_name from ProfileActivity: " + user_name, Toast.LENGTH_SHORT).show();

        } else {

        }*/

        // TODO pull tweets from intent
        //ArrayList<Tweet> tweets = getIntent().
        //addAll(Tweet.fromJSONArray(tweets));

        /*TwitterApplication.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonTweets) {
                addAll(Tweet.fromJSONArray(jsonTweets));
            }
        });*/

        /*Bundle bUser = getArguments();
        user = (User) bUser.getSerializable("user");

        if (bUser != null) {
            Toast.makeText(getActivity(), "UserTimelineFragment: " + user.getScreenName(), Toast.LENGTH_SHORT).show(); //user.getScreenName()
        } else {
            Toast.makeText(getActivity(), "UserTimelineFragment is NULL " , Toast.LENGTH_SHORT).show();
        }

        TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonTweets) {
                addAll(Tweet.fromJSONArray(jsonTweets));
            }
        }, user.getScreenName());*/

        String screenName = getArguments().getString("screenName");

        TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonTweets) {
                addAll(Tweet.fromJSONArray(jsonTweets));
            }
        }, screenName);

    }

    public void populateTimeline(long max_id){
        //client.getUserTimeline(getHandler());

        /*TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonTweets) {
                addAll(Tweet.fromJSONArray(jsonTweets));
            }
        }, user.getScreenName());*/


    }
}
