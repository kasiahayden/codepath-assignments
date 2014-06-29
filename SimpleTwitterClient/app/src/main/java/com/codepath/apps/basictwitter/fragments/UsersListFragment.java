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
import com.codepath.apps.basictwitter.UserArrayAdapter;
import com.codepath.apps.basictwitter.models.Collection;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;


public abstract class UsersListFragment extends Fragment { //FragmentActivity
    protected TwitterClient client;
    private ArrayList<User> users;
    private ArrayAdapter<User> aUsers;
    private ListView lvUsers;
    long maxUserId = Integer.MAX_VALUE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        users = new ArrayList<User>();
        //aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
        aUsers = new UserArrayAdapter(getActivity(), users); // No context within a fragment; must define activity
        // In general be very wary of calling getActivity, cause not one of the 3 ways to interact w/ activities/fragments

        populateUserList(-1);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        // Assign our view references
        // lvItems = ...

        lvUsers = (ListView) v.findViewById(R.id.lvTweets);
        lvUsers.setAdapter(aUsers);
        lvUsers.setOnScrollListener(new EndlessScrollListener() {
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

    public void addAll(ArrayList<User> users) {
        aUsers.addAll(users);
    }
    // TODO change to add all collections rather than users
    /*public void addAll(ArrayList<Collection> collections) {
        aUsers.addAll(collections);
    }*/

    public void add(User user) {
        aUsers.add(user);
    }

    public void notifyDataSetChanged() {
        aUsers.notifyDataSetChanged();
    }

    public void clear() {
        aUsers.clear();
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        populateUserList(maxUserId);

    }

    public JsonHttpResponseHandler getHandler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                Toast.makeText(getActivity(), "populateUserList success", Toast.LENGTH_SHORT).show();

                // TODO getting users not from json array but from parsing the collections object and looking up ids
                ArrayList<User> users = User.fromJSONArray(json); // TODO create function in User
                addAll(users); //
                //setMaxId(users);

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

    /*public void setMaxId(ArrayList<User> users) {
        for (User user : users) {
            long currentTweetId = user.getUserId(); // TODO make function in User
            if (currentTweetId < maxUserId) {
                maxUserId = currentTweetId - 1;
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

    public abstract void populateUserList(long max_id);



}