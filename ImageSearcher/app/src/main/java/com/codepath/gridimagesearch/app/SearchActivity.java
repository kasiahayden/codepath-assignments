package com.codepath.gridimagesearch.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    String query = "";
    GridView gvResults;
    Button btnSearch;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;
    private final int REQUEST_CODE = 4;
    SettingsResult settingsResult;
    private int startImageIndex = 0;
    private int incrementImageIndex = 4;
    String searchSettingsFragmentTitle = "Advanced Search Options";

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

    public SettingsResult getSettingsResult() {
        return this.settingsResult;
    }

    public void setSettingsResult(SettingsResult updatedSettingsResult) {
        this.settingsResult = updatedSettingsResult;
    }

    public void customLoadMoreDataFromApi(int offset) {
        String url = makeUrl();
        executeImageSearch(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    imageResults.clear();
                    query = s;
                    startImageIndex = 0;
                    if (isNetworkAvailable()) {
                        String url = makeUrl();
                        executeImageSearch(url);
                        return true;
                    } else {
                        Toast.makeText(getApplicationContext(), "Please connect to the internet to search", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void setupViews() {
        gvResults = (GridView) findViewById(R.id.gvResults);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            showEditDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected(); //activeNetworkInfo.isConnectedOrConnecting());
    }

    private void showEditDialog() {
        FragmentManager fm = getFragmentManager(); //getSupportFragmentManager();
        AdvanceSettingsDialog advanceSettingsDialog = AdvanceSettingsDialog.newInstance(searchSettingsFragmentTitle);
        advanceSettingsDialog.show(fm, "fragment_advanced_settings");
    }

}
