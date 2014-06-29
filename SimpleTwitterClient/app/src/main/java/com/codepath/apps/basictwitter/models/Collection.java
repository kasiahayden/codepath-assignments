package com.codepath.apps.basictwitter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by khayden on 6/29/14.
 */
public class Collection {
    private ArrayList<Long> ids;
    private Long next_cursor;
    private String next_cursor_str;
    private Long previous_cursor;
    private String previous_cursor_str;


    public void Collection() {

    }

    public static Collection fromJSON(JSONObject json) {
        Collection c = new Collection();
        try {
            // TODO how to get array from json?
            //c.ids = (ArrayList<Long>) json.get("ids"); //unchecked cast
            JSONArray idsArray = json.getJSONArray("ids");
            if (idsArray != null) {
                for (int i = 0; i < idsArray.length(); i++){
                    c.ids.add((Long) idsArray.get(i));
                }
            }
            c.ids = (ArrayList<Long>) json.get("ids");
            c.next_cursor = json.getLong("next_cursor");
            c.next_cursor_str = json.getString("next_cursor_str");
            c.previous_cursor = json.getLong("previous_cursor");
            c.previous_cursor_str = json.getString("previous_cursor_str");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c;
    }


    public Long getPrevious_cursor() {
        return previous_cursor;
    }

    public ArrayList<Long> getIds() {
        return ids;
    }

    public Long getNext_cursor() {
        return next_cursor;
    }

    public String getNext_cursor_str() {
        return next_cursor_str;
    }

    public String getPrevious_cursor_str() {
        return previous_cursor_str;
    }

}
