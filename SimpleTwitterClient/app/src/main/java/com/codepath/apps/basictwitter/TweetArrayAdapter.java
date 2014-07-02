package com.codepath.apps.basictwitter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by khayden on 6/19/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    Intent i;

    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        View v;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            v = inflator.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }

        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
        TextView tvScreenName = (TextView) v.findViewById(R.id.tvScreenName);
        TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        //TextView tvTimestamp = (TextView) v.findViewById(R.id.tvTimestamp);
        TextView tvRelativeTime = (TextView) v.findViewById(R.id.tvRelativeTime);
        int resId = android.R.color.transparent;
        ivProfileImage.setImageResource(resId);
        ImageLoader imageLoader = ImageLoader.getInstance();

        // Populate views with tweet data
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvScreenName.setText(tweet.getUser().getScreenName());
        tvUserName.setText(tweet.getUser().getUserName());
        tvBody.setText(tweet.getBody());
        //tvTimestamp.setText(tweet.getCreatedAt());
        tvRelativeTime.setText(tweet.getTimeSinceCreated());

        return v;
    }
}
