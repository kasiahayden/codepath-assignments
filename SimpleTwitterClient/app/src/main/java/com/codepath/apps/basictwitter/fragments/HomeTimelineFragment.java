package com.codepath.apps.basictwitter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.basictwitter.ComposeActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by khayden on 6/26/14.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    /*long maxTweetId = Integer.MAX_VALUE;
    private final int REQUEST_CODE = 20;
    User user;
    private TweetsListFragment fragmentTweetsList;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();

        populateTimeline(1, -1);
        /*setCurrentUser();
        fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        */
         /*
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }*/

    public void populateTimeline(long since_id, long max_id){
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                addAll(Tweet.fromJSONArray(json));

                /*//Toast.makeText(getApplicationContext(), "populateTimeline success", Toast.LENGTH_SHORT).show();
                Log.d("debug", json.toString());
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                setMaxId(tweets);*/
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
            }
        }, since_id, max_id);
    }

    /*public void setCurrentUser(){
        //Toast.makeText(getApplicationContext(), "getUser", Toast.LENGTH_SHORT).show();
        client.getCurrentUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.d("debug", json.toString());
                user = User.fromJSON(json);
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
            }
        });
    }*/

    /*public void setStatus(String status) {
        //Toast.makeText(getApplicationContext(), "postStatus: inside " + status, Toast.LENGTH_SHORT).show();
        client.postStatus(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                //Toast.makeText(getApplicationContext(), "postStatus: object " + response, Toast.LENGTH_LONG).show();
                //Log.d("debug", response);
                JSONObject json;// = new JSONObject();
                String created_at = "now";
                try {
                    json = new JSONObject(response);
                    Tweet tweet = Tweet.fromJson(json);
                    fragmentTweetsList.add(tweet);
                    fragmentTweetsList.notifyDataSetChanged();
                    fragmentTweetsList.clear();
                    populateTimeline(1, -1);

                    //created_at = json.getString("created_at");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "postStatus: created " + created_at, Toast.LENGTH_LONG).show();
                //Log.d("debug", "json: " + json.toString());
            }

            *//*public void onSuccess(JSONArray json) {

                String created_at = "now";
                try {
                    created_at = json.getJSONObject(0).getString("created_at");
                } catch (JSONException e) {
                    created_at = "fail";
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "postStatus: created " + created_at, Toast.LENGTH_LONG).show();
                Log.d("debug", "json: " + json.toString());
            }*//*

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
            }
        }, status);
    }*/

    // Append more data into the adapter
    /*public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        populateTimeline(1, maxTweetId);
    }*/

    /*public void setMaxId(ArrayList<Tweet> tweets) {
        for (Tweet tweet : tweets) {
            long currentTweetId = tweet.getTweetId();
            if (currentTweetId < maxTweetId) {
                maxTweetId = currentTweetId - 1;
                // "since the max_id parameter is inclusive,
                // the Tweet with the matching ID will actually be returned again...
                // Subtract 1 from the lowest Tweet ID returned from the previous request
                // and use this for the value of max_id. It does not matter if this
                // adjusted max_id is a valid Tweet ID, or if it corresponds with a
                // Tweet posted by a different user - the value is just used to
                // decide which Tweets to filter. When adjusted in this manner, it is
                // possible to page through a timeline without receiving redundant Tweets."
            }
        }
    }*/

    /*public void composeMessage() {
        //Toast.makeText(this, "compose message", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, ComposeActivity.class);
        i.putExtra("profile_image_url", user.getProfileImageUrl());
        i.putExtra("name", user.getName());
        i.putExtra("screen_name", user.getScreenName());
        startActivityForResult(i, REQUEST_CODE);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String tweet = data.getExtras().getString("tweet");
            //Toast.makeText(this, tweet, Toast.LENGTH_SHORT).show();
            setStatus(tweet);
        }
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.miCompose:
                composeMessage();
                return true;
            *//*case R.id.miProfile:
                showProfileView();
                return true;*//*
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}
