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
        setContentView(R.layout.activity_profile);
        profileUser = (User) getIntent().getSerializableExtra("user");
        //Toast.makeText(getApplicationContext(), "ProfileActivity.onCreate screenName: " + profileUser.getScreenName(), Toast.LENGTH_SHORT).show();
        screenName = profileUser.getScreenName();
        /*screenName = getIntent().getStringExtra("screenName");
        setAppUser(savedInstanceState);*/
        // TODO Disable going to own profile if click on own profile image
        populateProfileHeader();
        loadProfileInfo(savedInstanceState);
    }

    private void setProfileUser() {

    }

    /*public void setAppUser(final Bundle savedInstanceState) {
        TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                profileUser = User.fromJSON(json);
                Log.d("ProfileActivity.setAppUser", "screenName: " + profileUser.getScreenName());
                Toast.makeText(getApplicationContext(), "ProfileActivity.setAppUser screenName: " + profileUser.getScreenName(), Toast.LENGTH_SHORT).show();
                loadProfileInfo(savedInstanceState);
            }
        }, screenName);
    }*/

/*    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }*/

    private void loadProfileInfo(final Bundle savedInstanceState) {
        //Toast.makeText(this, "ProfileActivity: loadProfileInfo: " + screen_name, Toast.LENGTH_SHORT).show();
        TwitterApplication.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                //profileUser = User.fromJSON(json); //tweets.get(0).getUser();
                try {
                    getActionBar().setTitle("@" + screenName);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "ProfileActivity: loadProfileInfo: onSuccess: " + u.getScreenName(), Toast.LENGTH_SHORT).show();
                //populateUserTimeline(savedInstanceState, screenName);
                populateUserTimeline(savedInstanceState, tweets);
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
            }
        }, screenName);
    }



    private void populateProfileHeader() {
        Toast.makeText(getApplicationContext(), "ProfileActivity: populateProfileHeader", Toast.LENGTH_SHORT).show();

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        //TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        Button tvFollowing = (Button) findViewById(R.id.tvFollowing); //TODO change back
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(profileUser.getUserName());
        tvTagline.setText(profileUser.getTagline());
        tvFollowers.setText(profileUser.getFollowersCount() + " Followers");
        tvFollowing.setText(profileUser.getFriendsCount() + " Following!!!");
        ImageLoader.getInstance().displayImage(profileUser.getProfileImageUrl(), ivProfileImage);
    }

    private void populateUserTimeline(Bundle savedInstanceState, ArrayList<Tweet> tweets) { //String screenName) {//User u) {
        if (findViewById(R.id.flProfileContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }
            UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
            // TODO add User u argument
            /*Bundle bUser = new Bundle();
            bUser.putSerializable("user", u);
            //bUser.putString("test", "123");
            userTimelineFragment.setArguments(bUser);*/
            /*Bundle bUser = new Bundle();
            bUser.putString("screenName", screenName);*/

            Bundle bTweets = new Bundle();
            bTweets.putSerializable("tweets", tweets);
            userTimelineFragment.setArguments(bTweets);



            //userTimelineFragment.setArguments(getIntent().getExtras());
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
        //Toast.makeText(this, "ProfileActivity: onClickFollowers", Toast.LENGTH_SHORT).show();
        Log.d("ProfileActivity", "onClickFollowers");
        Intent i = new Intent(this, FollowActivity.class);
        Toast.makeText(this, "ProfileActivity: onClickFollowers: " + profileUser.getScreenName(), Toast.LENGTH_SHORT).show();
        i.putExtra("user", profileUser);
        startActivity(i);
    }

    public void onClickFollowing(View v) {
        //Toast.makeText(this, "ProfileActivity: onClickFollowing", Toast.LENGTH_SHORT).show();
        Log.d("ProfileActivity", "onClickFollowing");
        Intent i = new Intent(this, FollowActivity.class);
        //startActivity(i);
    }
}
