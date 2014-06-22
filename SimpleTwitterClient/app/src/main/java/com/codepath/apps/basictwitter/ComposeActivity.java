package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvScreenName;
    TextView tvCharacterCount;
    String profileImageUrl;
    String name;
    String screen_name;
    int characterCount;
    Button btnTweet;
    EditText etBody;
    final static int charactersPerTweet = 140;
    int remainingCharacters = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        setupViews();

        profileImageUrl = getIntent().getStringExtra("profile_image_url");
        name = getIntent().getStringExtra("name");
        screen_name = getIntent().getStringExtra("screen_name");

        ivProfileImage.setImageResource(android.R.color.transparent);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(profileImageUrl, ivProfileImage);
        tvName.setText(name);
        tvScreenName.setText("@" + screen_name);

        etBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)

                //Toast.makeText(getApplicationContext(), "count: " + s.length(), Toast.LENGTH_SHORT).show();
                characterCount = s.length();
                remainingCharacters = charactersPerTweet - characterCount;
                tvCharacterCount.setText(Integer.toString(remainingCharacters));

                if (remainingCharacters < 10 & remainingCharacters >= 0) {
                    tvCharacterCount.setTextColor(Color.YELLOW);
                    btnTweet.setEnabled(true);
                } else if (remainingCharacters < 0) {
                    tvCharacterCount.setTextColor(Color.RED);
                    btnTweet.setEnabled(false);
                } else {
                    tvCharacterCount.setTextColor(Color.WHITE);
                    btnTweet.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
            }
        });
    }

    public void setupViews() {
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName = (TextView) findViewById(R.id.tvName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvCharacterCount = (TextView) findViewById(R.id.tvCharacterCount);
        btnTweet = (Button) findViewById(R.id.btnTweet);
        etBody = (EditText) findViewById(R.id.tvBody);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
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

    public void onSubmit(View v) {
        if (remainingCharacters >= 0) {
            Intent data = new Intent();
            data.putExtra("tweet", etBody.getText().toString());
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
