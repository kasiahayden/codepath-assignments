package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

/**
 * Created by khayden on 6/26/14.
 */
public class MentionsTimelineFragment extends TweetsListFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO endless pagination in TweetsListFragment
        populateTimeline(-1);
    }

    public void populateTimeline(long max_id){
        client.getMentionsTimeline(getHandler(), max_id);
    }
}
