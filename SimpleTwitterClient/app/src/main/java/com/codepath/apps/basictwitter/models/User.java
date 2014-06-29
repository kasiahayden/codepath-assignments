package com.codepath.apps.basictwitter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by khayden on 6/19/14.
 */
public class User implements Serializable { //TODO change to User extends BaseModel
// TODO Serializable still necessary?
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String description;
    private String friendsCount;
    private String followersCount;

    public static User fromJSON(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.description = json.getString("description");
            u.friendsCount = json.getString("friends_count");
            u.followersCount = json.getString("followers_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    public static ArrayList<User> fromJSONArray(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<User>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userJson = null;
            try {
                userJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            User user = User.fromJSON(userJson);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getFriendsCount() {
        return friendsCount;
    }

    public String getTagline() {
        // TODO make each method like: return getString("description");
        return description;
    }

    public String getFollowersCount() {
        return followersCount;
    }
}
