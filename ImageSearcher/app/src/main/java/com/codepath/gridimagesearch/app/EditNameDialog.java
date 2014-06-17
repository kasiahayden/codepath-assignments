package com.codepath.gridimagesearch.app;


import android.content.Context;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by khayden on 6/17/14.
 */
public class EditNameDialog extends DialogFragment {
    Button btnSaveSettings;
    Spinner snrImageSize;
    Spinner snrColorFilter;
    Spinner snrImageType;
    EditText etSiteFilter;

    //private EditText mEditText;

    public EditNameDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditNameDialog newInstance(String title) {
        EditNameDialog frag = new EditNameDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_name, container);
        String title = getArguments().getString("title");
        getDialog().setTitle(title);

        snrImageSize = (Spinner) view.findViewById(R.id.snrImageSize);
        snrColorFilter = (Spinner) view.findViewById(R.id.snrColorFilter);
        snrImageType = (Spinner) view.findViewById(R.id.snrImageType);
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);

        ArrayAdapter<CharSequence> adapterImageSize = ArrayAdapter.createFromResource(getActivity(),
                R.array.arrImageSize, android.R.layout.simple_spinner_item);
        adapterImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrImageSize.setAdapter(adapterImageSize);

        ArrayAdapter<CharSequence> adapterColorFilter = ArrayAdapter.createFromResource(getActivity(),
                R.array.arrColorFilter, android.R.layout.simple_spinner_item);
        adapterColorFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrColorFilter.setAdapter(adapterColorFilter);

        ArrayAdapter<CharSequence> adapterImageType = ArrayAdapter.createFromResource(getActivity(),
                R.array.arrImageType, android.R.layout.simple_spinner_item);
        adapterImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snrImageType.setAdapter(adapterImageType);

        btnSaveSettings = (Button) view.findViewById(R.id.btnSaveSettings);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSubmit(v);
            }
        });

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view;
    }

    public void onSubmit(View v) {

        SettingsResult updatedSettingsResult = new SettingsResult(
                snrImageSize.getSelectedItem().toString(),
                snrColorFilter.getSelectedItem().toString(),
                snrImageType.getSelectedItem().toString(),
                etSiteFilter.getText().toString()
        );

        /*

        Intent i = new Intent();
        i.putExtra("settings", settingsResult);
        setResult(RESULT_OK, i);
        finish();*/

        ((SearchActivity) getActivity()).setSettingsResult(updatedSettingsResult);

        getDialog().dismiss();

    }


}