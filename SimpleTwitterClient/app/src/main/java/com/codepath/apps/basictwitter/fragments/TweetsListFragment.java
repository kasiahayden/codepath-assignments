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


public abstract class TweetsListFragment extends Fragment {
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
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
        populateTimeline(-1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });
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

    public void customLoadMoreDataFromApi(int offset) {
        populateTimeline(maxTweetId);
    }

    public JsonHttpResponseHandler getHandler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                addAll(tweets);
                setMaxId(tweets);
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
