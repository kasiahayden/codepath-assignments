package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "KilipS7dDxe2HbpEd4plWjQVm";       // Change this
    public static final String REST_CONSUMER_SECRET = "AbvY9yaF2aQzZ8tIhxyvMLN4trlIAOw0iNZfbZdDvtQj29c3FG"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // https://api.twitter.com/1.1/statuses/home_timeline.json
    //public void getHomeTimeline(AsyncHttpResponseHandler handler, long since_id, long max_id) {
    public void getHomeTimeline(AsyncHttpResponseHandler handler, long max_id) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("max_id", Long.toString(max_id)); //
        if (max_id >= 0) {
            requestParams.put("max_id", Long.toString(max_id));
        }
        client.get(apiUrl, null, handler);
    }

    /*public void getHomeTimeline(AsyncHttpResponseHandler handler, long max_id) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("max_id", Long.toString(max_id)); //
        client.get(apiUrl, null, handler);
    }*/

    //https://api.twitter.com/1.1/account/verify_credentials.json
    public void getMyInfo(AsyncHttpResponseHandler handler) { //formerly getCurrentUser
        String apiUrl = getApiUrl("account/verify_credentials.json");
        client.get(apiUrl, null, handler);
    }

    //https://api.twitter.com/1.1/users/show.json
    public void getUserInfo(AsyncHttpResponseHandler handler, String screen_name) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("screen_name", screen_name);
        client.get(apiUrl, requestParams, handler);
    }

    //https://api.twitter.com/1.1/statuses/update.json
    public void postStatus(AsyncHttpResponseHandler handler, String status) {
        //Log.d("debug", "postStatus: " + status);
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("status", status);
        client.post(apiUrl, requestParams, handler);
    }

    // TODO change this to support pagination with maxID
    //https://api.twitter.com/1.1/statuses/mentions_timeline.json
    //public void getMentionsTimeline(AsyncHttpResponseHandler handler, long since_id, long max_id) { //was Json
    public void getMentionsTimeline(AsyncHttpResponseHandler handler, long max_id) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        client.get(apiUrl, null, handler);
    }

    public void getUserTimeline(AsyncHttpResponseHandler handler, String screen_name) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("screen_name", screen_name);
        client.get(apiUrl, requestParams, handler);
    }

    // https://api.twitter.com/1.1/friends/ids.json
    public void getFriendsIds(JsonHttpResponseHandler handler, String screen_name) {
        String apiUrl = "friends/ids.json";
        RequestParams requestParams = new RequestParams();
        requestParams.put("screen_name", screen_name);
        client.get(apiUrl, requestParams, handler);
    }

    //https://api.twitter.com/1.1/users/lookup.json
    public void usersLookup(AsyncHttpResponseHandler handler, String user_id) {
        String apiUrl = "users/lookup.json";
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", user_id);
        client.get(apiUrl, requestParams, handler);
    }


    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    /*public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }*/
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}