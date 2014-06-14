package com.codepath.gridimagesearch.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class AdvancedSearchOptions extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search_options);

        Spinner snrImageSize = (Spinner) findViewById(R.id.snrImageSize);
        ArrayAdapter<CharSequence> adapterImageSize = ArrayAdapter.createFromResource(this,
                R.array.arrImageSize, android.R.layout.simple_spinner_item);
        adapterImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrImageSize.setAdapter(adapterImageSize);

        Spinner snrColorFilter = (Spinner) findViewById(R.id.snrColorFilter);
        ArrayAdapter<CharSequence> adapterColorFilter = ArrayAdapter.createFromResource(this,
                R.array.arrColorFilter, android.R.layout.simple_spinner_item);
        adapterColorFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrColorFilter.setAdapter(adapterColorFilter);


        Spinner snrImageType = (Spinner) findViewById(R.id.snrImageType);
        ArrayAdapter<CharSequence> adapterImageType = ArrayAdapter.createFromResource(this,
                R.array.arrImageType, android.R.layout.simple_spinner_item);
        adapterImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrImageType.setAdapter(adapterImageType);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.advanced_search_options, menu);
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
