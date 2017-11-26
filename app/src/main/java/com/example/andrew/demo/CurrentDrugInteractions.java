package com.example.andrew.demo;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CurrentDrugInteractions extends AppCompatActivity {
    private static final String TAG = CurrentDrugInteractions.class.getSimpleName();
    DrugDrugFragment drugDrugFragment;
    DrugFoodFragment drugFoodFragment;
    DrugAlcoholFragment drugAlcoholFragment;
    DrugLifestyleFragment drugLifestyleFragment;
    ArrayList<String> listOfData;
    int oldPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_drug_interactions);

        drugDrugFragment = new DrugDrugFragment();
        drugFoodFragment = new DrugFoodFragment();
        drugAlcoholFragment = new DrugAlcoholFragment();
        drugLifestyleFragment = new DrugLifestyleFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);  // clear all scroll flags

        listOfData = new ArrayList<>();
        InputStream is = this.getResources().openRawResource(R.raw.input_data);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        int position = 0;
        listOfData.clear();
        try {
            br.readLine(); //skips the first line because those are titles
            while ((line = br.readLine()) != null) {
                if (line.startsWith("DDI")
                        || line.startsWith("DFI")
                        || line.startsWith("DAI")
                        || line.startsWith("DLI")) {
                    listOfData.add(position, line);
                    position++;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "FAILED TO READ FILE");
        }

        //TODO: What are the titles for the different drug interactions, and where are the images for the chart
        // Setup spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(
                toolbar.getContext(),
                new String[]{
                        "Drug-Drug",
                        "Drug-Food",
                        "Drug-Alcohol",
                        "Drug-Lifestyle"
                }));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                switch (position) {
                    case 0:
                        if (listOfData != null) {
                            for (String s : listOfData) {
                                if (s.contains("DDI")) {
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, drugDrugFragment)
                                            .commit();
                                    oldPosition = -1; //impossible value, saying that selection is successful
                                }
                            }
                        }
                        if (oldPosition != -1) {
                            spinner.setSelection(oldPosition);
                            Toast.makeText(CurrentDrugInteractions.this, "Interaction not applicable", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if (listOfData != null) {
                            for (String s : listOfData) {
                                if (s.contains("DFI")) {
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, drugFoodFragment)
                                            .commit();
                                    oldPosition = -1; //impossible value, saying that selection is successful
                                }
                            }
                        }
                        if (oldPosition != -1) {
                            spinner.setSelection(oldPosition);
                            Toast.makeText(CurrentDrugInteractions.this, "Interaction not applicable", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (listOfData != null) {
                            for (String s : listOfData) {
                                if (s.contains("DAI")) {
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, drugAlcoholFragment)
                                            .commit();
                                    oldPosition = -1; //impossible value, saying that selection is successful
                                }
                            }
                        }
                        if (oldPosition != -1) {
                            spinner.setSelection(oldPosition);
                            Toast.makeText(CurrentDrugInteractions.this, "Interaction not applicable", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if (listOfData != null) {
                            for (String s : listOfData) {
                                if (s.contains("DLI")) {
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, drugLifestyleFragment)
                                            .commit();
                                    oldPosition = -1; //impossible value, saying that selection is successful
                                }
                            }
                        }
                        if (oldPosition != -1) {
                            spinner.setSelection(oldPosition);
                            Toast.makeText(CurrentDrugInteractions.this, "Interaction not applicable", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

                oldPosition = position; //if no info found for view, reverts selection to previous
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { //required implemented method
            }
        });
    }


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }
}
