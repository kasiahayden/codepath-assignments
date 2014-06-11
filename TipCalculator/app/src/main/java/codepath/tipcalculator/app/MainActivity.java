package codepath.tipcalculator.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements TextWatcher {
    String TAG = "MainActivity";
    public EditText etTransactionTotal;
    public TextView tvTipTotalOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTransactionTotal = (EditText) findViewById(R.id.etTransactionTotal);
        etTransactionTotal.addTextChangedListener(this);
        tvTipTotalOutput = (TextView) findViewById(R.id.tvTipTotalOutput);



        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgTipAmounts);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                double newTransactionTotal = Double.parseDouble(etTransactionTotal.getText().toString());
                switch (checkedId) {
                    case R.id.rbp10:
                        //Log.d(TAG, "p10 selected");
                        newTransactionTotal *= 0.10;
                        tvTipTotalOutput.setText("$" + String.format("%.2f", newTransactionTotal));
                        break;
                    case R.id.rbp15:
                        //Log.d(TAG, "p15 selected");
                        newTransactionTotal *= 0.15;
                        tvTipTotalOutput.setText("$" + String.format("%.2f", newTransactionTotal));
                        break;
                    case R.id.rbp20:
                        //Log.d(TAG, "p20 selected");
                        newTransactionTotal *= 0.20;
                        tvTipTotalOutput.setText("$" + String.format("%.2f", newTransactionTotal));
                        break;
                }
                // checkedId is the RadioButton selected
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        double newTransactionTotal = Double.parseDouble(editable.toString());
        tvTipTotalOutput.setText("$" + String.format("%.2f", newTransactionTotal));
    }
}
