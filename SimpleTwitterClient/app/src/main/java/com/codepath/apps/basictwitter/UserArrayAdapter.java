package com.codepath.apps.basictwitter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by khayden on 6/19/14.
 */
public class UserArrayAdapter extends ArrayAdapter<User> {
    Intent i;

    public UserArrayAdapter(Context context, List<User> users) {
        super(context, 0, users);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
        //Tweet tweet = getItem(position);
        // find or inflate the template
        View v;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            v = inflator.inflate(R.layout.user_item, parent, false);
        } else {
            v = convertView;
        }
        /*// Find the views within the template
        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        //TextView tvTimestamp = (TextView) v.findViewById(R.id.tvTimestamp);
        TextView tvRelativeTime = (TextView) v.findViewById(R.id.tvRelativeTime);
        //ivProfileImage.setImageResource(android.R.color.transparent);
        int resId = android.R.color.transparent;
        //getContext().getResources().getDrawable(android.R.color.transparent)
        ivProfileImage.setImageResource(resId);
        ImageLoader imageLoader = ImageLoader.getInstance();
        // Populate views with tweet data
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvUserName.setText(tweet.getUser().getScreenName());
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        //tvTimestamp.setText(tweet.getCreatedAt());
        tvRelativeTime.setText(tweet.getTimeSinceCreated());*/

        return v;
    }
}
