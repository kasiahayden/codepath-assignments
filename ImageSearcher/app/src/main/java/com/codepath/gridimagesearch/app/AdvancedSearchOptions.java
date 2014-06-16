package com.codepath.gridimagesearch.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class AdvancedSearchOptions extends Activity {
    Button btnSaveSettings;
    Spinner snrImageSize;
    Spinner snrColorFilter;
    Spinner snrImageType;
    EditText etSiteFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search_options);

        setupViews();

        ArrayAdapter<CharSequence> adapterImageSize = ArrayAdapter.createFromResource(this,
                R.array.arrImageSize, android.R.layout.simple_spinner_item);
        adapterImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrImageSize.setAdapter(adapterImageSize);

        ArrayAdapter<CharSequence> adapterColorFilter = ArrayAdapter.createFromResource(this,
                R.array.arrColorFilter, android.R.layout.simple_spinner_item);
        adapterColorFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrColorFilter.setAdapter(adapterColorFilter);

        ArrayAdapter<CharSequence> adapterImageType = ArrayAdapter.createFromResource(this,
                R.array.arrImageType, android.R.layout.simple_spinner_item);
        adapterImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrImageType.setAdapter(adapterImageType);

        btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSubmit(v);
            }
        });
    }

    public void setupViews() {
        snrImageSize = (Spinner) findViewById(R.id.snrImageSize);
        snrColorFilter = (Spinner) findViewById(R.id.snrColorFilter);
        snrImageType = (Spinner) findViewById(R.id.snrImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
    }


    public void onSubmit(View v) {

        SettingsResult settingsResult = new SettingsResult(
                snrImageSize.getSelectedItem().toString(),
                snrColorFilter.getSelectedItem().toString(),
                snrImageType.getSelectedItem().toString(),
                etSiteFilter.getText().toString()
        );

        Intent i = new Intent();
        i.putExtra("settings", settingsResult);
        setResult(RESULT_OK, i);
        finish();

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
