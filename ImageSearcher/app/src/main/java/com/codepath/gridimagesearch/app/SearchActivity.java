package com.codepath.gridimagesearch.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends Activity {
    EditText etQuery;
    String query = "";
    GridView gvResults;
    Button btnSearch;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;
    private final int REQUEST_CODE = 4;
    SettingsResult settingsResult;
    private int startImageIndex = 0;
    private int incrementImageIndex = 4;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        settingsResult = new SettingsResult();

        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                //i.putExtra("url", imageResult.getFullUrl());
                i.putExtra("result", imageResult);
                startActivity(i);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });


    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        Toast.makeText(this, "scrolling", Toast.LENGTH_LONG).show();

        String url = makeUrl();
        Toast.makeText(this, "url: " + url, Toast.LENGTH_LONG).show();
        executeImageSearch(url);

        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;

        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main, menu);
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //searchView = (SearchView) searchItem.getActionView();
        //Toast.makeText(this, "searchView null?: " + (searchView == null), Toast.LENGTH_LONG).show();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // perform query here
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//        });
        //return super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu, menu);
        inflater.inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        //Toast.makeText(this, "searchView null?: " + (searchView == null), Toast.LENGTH_LONG).show();
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    imageResults.clear();
                    //query = etQuery.getText().toString();
                    query = s;
                    Toast.makeText(getApplicationContext(), "Searching for " + query, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "testing", Toast.LENGTH_LONG).show();
                    startImageIndex = 0;
                    String url = makeUrl();
                    Toast.makeText(getApplicationContext(), "url: " + url, Toast.LENGTH_LONG).show();
                    executeImageSearch(url);
                    return true; //matters if true or false?
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    //imageResults.clear();
                    return false;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);


    }

    public void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
    }

    public void onImageSearch(View v) {
        imageResults.clear();
        query = etQuery.getText().toString();
        Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();
        startImageIndex = 0;
        String url = makeUrl();
        Toast.makeText(this, "url: " + url, Toast.LENGTH_LONG).show();
        executeImageSearch(url);
    }

    public String makeUrl() {
        return "https://ajax.googleapis.com/ajax/services/search/images?/rsz=" + incrementImageIndex + "&" +
                //"start=" + 0 + "&v=1.0&q=" + Uri.encode(query), new JsonHttpResponseHandler(){
                "start=" + startImageIndex + "&v=1.0&q=" + Uri.encode(query) +
                "&imgsz=" + Uri.encode(settingsResult.getImageSize()) +
                "&imgcolor=" + Uri.encode(settingsResult.getColorFilter()) +
                "&imgtype=" + Uri.encode(settingsResult.getSiteFilter())
                ;
    }

    public void executeImageSearch(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            public void onSuccess(JSONObject response) {
                JSONArray imageJsonResults = null;
                try {
                    imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResults.clear();
                    imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
                    startImageIndex += incrementImageIndex;
                    Log.d("DEBUG", imageResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), AdvancedSearchOptions.class);
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            //String name = data.getExtras().getString("name");
            //settingsResult = (SettingsResult) getIntent().getSerializableExtra("settings");
            settingsResult = (SettingsResult) data.getSerializableExtra("settings");
            //settingsResult.getImageSize();
            // Toast the name to display temporarily on screen
            Log.d("SearchActivity", "from bundle: " + settingsResult.getSiteFilter());
//            Toast.makeText(this, "Passed data: " +
//                    settingsResult.getImageSize() + ", " +
//                    settingsResult.getColorFilter() + ", " +
//                    settingsResult.getImageType() + ", " +
//                    settingsResult.getSiteFilter()
//                    , Toast.LENGTH_SHORT).show();

            //edittext messed up: Toast.makeText(this, "from bundle: " + settingsResult.getSiteFilter(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, "from bundle: " + settingsResult.getImageSize() + ", " + settingsResult.getColorFilter() + ", " + settingsResult.getImageType() + ", " + settingsResult.getSiteFilter(), Toast.LENGTH_LONG).show();
            //this.settingsResult.setImageSize();
        }
    }
}
