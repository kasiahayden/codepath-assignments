package com.codepath.apps.basictwitter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.ComposeActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by khayden on 6/26/14.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    //private TwitterClient client;
    private final int REQUEST_CODE = 20;
    User user;
    private TweetsListFragment fragmentTweetsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //client = TwitterApplication.getRestClient();

        //populateTimeline(-1);
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

    public void populateTimeline(long max_id){
        client.getHomeTimeline(getHandler(), max_id);
    }


    public void setCurrentUser(){
        //Toast.makeText(getApplicationContext(), "getUser", Toast.LENGTH_SHORT).show();
        client.getMyInfo(new JsonHttpResponseHandler() {
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
    }

    public void setStatus(String status) {
        client.postStatus(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                JSONObject json;// = new JSONObject();
                String created_at = "now";
                try {
                    json = new JSONObject(response);
                    Tweet tweet = Tweet.fromJSON(json);
                    fragmentTweetsList.add(tweet);
                    fragmentTweetsList.notifyDataSetChanged();
                    fragmentTweetsList.clear();
                    populateTimeline(-1);

                    //created_at = json.getString("created_at");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onSuccess(JSONArray json) {

                String created_at = "now";
                try {
                    created_at = json.getJSONObject(0).getString("created_at");
                } catch (JSONException e) {
                    created_at = "fail";
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "postStatus: created " + created_at, Toast.LENGTH_LONG).show();
                Log.d("debug", "json: " + json.toString());
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
            }
        }, status);
    }

    /*public void customLoadMoreDataFromApi(int offset) {
        populateTimeline(1, maxTweetId);
    }*/



    public void composeMessage() {
        //Toast.makeText(this, "compose message", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getActivity(), ComposeActivity.class);
        i.putExtra("profile_image_url", user.getProfileImageUrl());
        i.putExtra("name", user.getUserName());
        i.putExtra("screen_name", user.getScreenName());
        startActivityForResult(i, REQUEST_CODE);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String tweet = data.getExtras().getString("tweet");
            //Toast.makeText(this, tweet, Toast.LENGTH_SHORT).show();
            setStatus(tweet);
        }
    }*/

    @Override
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
            /*case R.id.miProfile:
                showProfileView();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
