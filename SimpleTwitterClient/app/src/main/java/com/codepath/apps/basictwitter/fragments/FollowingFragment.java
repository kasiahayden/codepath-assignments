package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class FollowingFragment extends UsersListFragment { // TODO change to extend FollowListFragment over TweetsListFragment
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bUser = getArguments();
        user = (User) bUser.getSerializable("user");
        Toast.makeText(getActivity(), "FollowingFragment: getScreenName: " + user.getScreenName(), Toast.LENGTH_SHORT).show();
        //addAll(tweets);

        // TODO change to get following api call:

       /* TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonTweets) {
                addAll(Tweet.fromJSONArray(jsonTweets));
            }
        }, user.getScreenName());*/

        /*TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonUsers) {
                addAll(User.fromJSONArray(jsonUsers));
            }
        }, "khaydentester");*/

        // Change to return who you're following
        TwitterApplication.getRestClient().getFollowing(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {

                //addAll(User.fromJSONArray(json));
            }
        }, "khaydentester");

    }

    public void populateUserList(long max_id){
    }
}