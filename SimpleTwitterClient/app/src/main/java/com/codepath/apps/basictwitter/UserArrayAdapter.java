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
        View v;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            v = inflator.inflate(R.layout.user_item, parent, false);
        } else {
            v = convertView;
        }
        return v;
    }
}
