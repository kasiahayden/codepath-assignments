package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowingFragment extends UsersListFragment { // TODO change to extend FollowListFragment over TweetsListFragment
    User user;
    String friendsIds = "ok";
    String previous_cursor_str = "444";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bUser = getArguments();
        user = (User) bUser.getSerializable("user");
        Toast.makeText(getActivity(), "FollowingFragment: getScreenName: " + user.getScreenName(), Toast.LENGTH_SHORT).show();
        getFriendsIds();
    }

    public void getFriendsIds() {
        // Change to return who you're following
        //showProgressBar();
        TwitterApplication.getRestClient().getFriendsIds(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    Log.d("getFriendsIds", "SUCCESS");
                    friendsIds = (String) json.get("id");
                    previous_cursor_str = json.getString("previous_cursor_str");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(), "getFriendsIds: " + friendsIds, Toast.LENGTH_SHORT).show();
                Log.d("getFriendsIds", friendsIds);
                //usersLookup();


                //addAll(User.fromJSONArray(json));
            }

            public void onSuccess(JSONArray jsonArray) {
                Toast.makeText(getActivity(), "getFriendsIds: ARRAY", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                Log.d("getFriendsIds", "inside FAIL");
            }
        }, "khaydentester"); //user.getScreenName()
       //hideProgressBar();
    }

    /*public void usersLookup() {
        TwitterApplication.getRestClient().usersLookup(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject json) {
                try {

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getFriends();


                //addAll(User.fromJSONArray(json));
            }
        }, friendsIds);
    }*/

    public void populateUserList(long max_id){
    }
}