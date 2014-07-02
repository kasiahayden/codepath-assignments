package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBar.TabListener;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends FragmentActivity implements TabListener {
    User appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_timeline);
        setupTabs();
        if (isOnline() || isNetworkAvailable()) {
            setAppUser();
        } else {
            Toast.makeText(this, "You're not connected to the internet", Toast.LENGTH_SHORT).show();
            Log.d("TimelineActivity", "No internet");
        }
    }

    // Should be called manually when an async task has started
    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true);
    }

    // Should be called when an async task has finished
    public void hideProgressBar() {
        setProgressBarIndeterminateVisibility(false);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setIcon(R.drawable.ic_home)
                .setTag("HomeTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
                                HomeTimelineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Mentions")
                .setIcon(R.drawable.ic_mentions)
                .setTag("MentionsTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
                                MentionsTimelineFragment.class));

        actionBar.addTab(tab2);
    }

    public void setAppUser() {
        showProgressBar();
        TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                appUser = User.fromJSON(json);
            }
        });
        hideProgressBar();
    }

    public void onProfileView(MenuItem mi) {
        startProfileActivity(appUser);
    }

    public void onProfileImageSelected(View v) {
        // TODO fix bug: top view visible in screen is selected
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        String screenName = tvScreenName.getText().toString();
        //Toast.makeText(this, "TimelineActivity: onProfileImageSelected: sn: " + screenName, Toast.LENGTH_SHORT).show();
        Log.d("TimelineActivity", "onProfileImageSelected: sn: " + screenName);
        showProgressBar();
        TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                User user = User.fromJSON(json);
                //Toast.makeText(getApplicationContext(), "TimelineActivity: getUserInfo: sn: " + user.getScreenName(), Toast.LENGTH_SHORT).show();
                startProfileActivity(user);
            }
        }, screenName);
        hideProgressBar();
    }

    public void startProfileActivity(User user) {
        if (isOnline() || isNetworkAvailable()) {
            Intent i = new Intent(this, ProfileActivity.class);
            i.putExtra("user", user);
            startActivity(i);
        } else {
            Toast.makeText(this, "You're not connected to the internet", Toast.LENGTH_SHORT).show();
            Log.d("TimelineActivity.StartProfile", "No internet");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweets, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }
}
