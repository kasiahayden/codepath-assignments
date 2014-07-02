package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.fragments.FollowersFragment;
import com.codepath.apps.basictwitter.fragments.FollowingFragment;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class FollowActivity extends FragmentActivity {
    User profileUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        profileUser = (User) getIntent().getSerializableExtra("user");
        populateFollowList(savedInstanceState, profileUser);
    }


    private void populateFollowList(Bundle savedInstanceState, User user) { //Pass current user's profile
        if (findViewById(R.id.flFollowContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }

            FollowingFragment followingFragment = new FollowingFragment();
            Bundle bUser = new Bundle();
            bUser.putSerializable("user", user);
            followingFragment.setArguments(bUser);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flFollowContainer, followingFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.follow, menu);
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
}
