package com.codepath.apps.basictwitter.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.ProfileActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;


public abstract class TweetsListFragment extends Fragment { //FragmentActivity
    protected TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;
    long maxTweetId = Integer.MAX_VALUE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<Tweet>();
        //aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
        aTweets = new TweetArrayAdapter(getActivity(), tweets); // No context within a fragment; must define activity
        // In general be very wary of calling getActivity, cause not one of the 3 ways to interact w/ activities/fragments

        populateTimeline(-1);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        // Assign our view references
        // lvItems = ...

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });

        // Return the layout view
        return v;
    }

    public void addAll(ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void add(Tweet tweet) {
        aTweets.add(tweet);
    }

    public void notifyDataSetChanged() {
        aTweets.notifyDataSetChanged();
    }

    public void clear() {
        aTweets.clear();
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        populateTimeline(maxTweetId);

    }

    public JsonHttpResponseHandler getHandler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                //Toast.makeText(getActivity(), "onSuccess from TweetsListFragment getHandler", Toast.LENGTH_SHORT).show();
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                addAll(tweets);
                setMaxId(tweets);

                /*//Toast.makeText(getApplicationContext(), "populateTimeline success", Toast.LENGTH_SHORT).show();
                Log.d("debug", json.toString());
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                setMaxId(tweets);*/
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
            }
        };
    }

    public void setMaxId(ArrayList<Tweet> tweets) {
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
    }

    public abstract void populateTimeline(long max_id);



}
