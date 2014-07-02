package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends FragmentActivity {
    User profileUser;
    String screenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_profile);
        profileUser = (User) getIntent().getSerializableExtra("user");screenName = profileUser.getScreenName();
        // TODO Disable going to own profile if click on own profile image
        populateProfileHeader();
        loadProfileInfo(savedInstanceState);
    }

    // Should be called manually when an async task has started
    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true);
    }

    // Should be called when an async task has finished
    public void hideProgressBar() {
        setProgressBarIndeterminateVisibility(false);
    }

    private void loadProfileInfo(final Bundle savedInstanceState) {
        showProgressBar();
        TwitterApplication.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                try {
                    getActionBar().setTitle("@" + screenName);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                populateUserTimeline(savedInstanceState, tweets);
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
            }
        }, screenName);
        hideProgressBar();
    }



    private void populateProfileHeader() {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(profileUser.getUserName());
        tvTagline.setText(profileUser.getTagline());
        tvFollowers.setText(profileUser.getFollowersCount() + " Followers");
        tvFollowing.setText(profileUser.getFriendsCount() + " Following");
        ImageLoader.getInstance().displayImage(profileUser.getProfileImageUrl(), ivProfileImage);
    }

    private void populateUserTimeline(Bundle savedInstanceState, ArrayList<Tweet> tweets) { //String screenName) {//User u) {
        if (findViewById(R.id.flProfileContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }
            UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
            Bundle bTweets = new Bundle();
            bTweets.putSerializable("pTweets", tweets);
            userTimelineFragment.setArguments(bTweets);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flProfileContainer, userTimelineFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickFollowers(View v) {
        Log.d("ProfileActivity", "onClickFollowers");
        Intent i = new Intent(this, FollowActivity.class);
        i.putExtra("user", profileUser);
        startActivity(i);
    }

    public void onClickFollowing(View v) { //Friends
        Log.d("ProfileActivity", "onClickFollowing");
        Intent i = new Intent(this, FollowActivity.class);
        i.putExtra("user", profileUser);
        startActivity(i);
    }
}
