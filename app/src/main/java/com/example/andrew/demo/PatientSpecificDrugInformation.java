package com.example.andrew.demo;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.database.MatrixCursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class PatientSpecificDrugInformation extends AppCompatActivity {

    public static final String TAG = PatientSpecificDrugInformation.class.getName();
    ZoomRecyclerView zlvChart;
    ArrayList<String> listOfData = new ArrayList<>();
    RecyclerViewAdapterPatientSpecificDrugInformation recyclerViewAdapter;

    TextView tvCaption;
    TextView tvName;
    TextView tvSummary;

    DiagonalHorizontalScrollView diagonalHorizontalScrollView;

    MySimpleCursorAdapter simpleCursorAdapter;

    ArrayList<String> suggestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_specific_drug_information);

        tvCaption = (TextView) findViewById(R.id.tvCaption);
        tvName = (TextView) findViewById(R.id.tvName);
        tvSummary = (TextView) findViewById(R.id.tvSummary);

        InputStream is = this.getResources().openRawResource(R.raw.input_data);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        int position = 0;

        try {
            br.readLine(); //skips the first line because those are titles
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("ZNA") //does not include drug interactions and certain other data
                        && !line.startsWith("DDI")
                        && !line.startsWith("DFI")
                        && !line.startsWith("DAI")
                        && !line.startsWith("DLI")
                        && !line.startsWith("\t")) {

                    int columnNumber = 1;
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '\t') {
                            columnNumber++;
                            if (columnNumber == 9
                                    && line.charAt(i + 1) == 'T') {
                                listOfData.add(position, line);
                                position++;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "FAILED TO READ FILE");
        }

        //setting the TextViews at the top
        tvCaption.setText("ICD-10: B20 Human Immunodeficiency virus [HIV] disease");
        tvName.setText("Smith, John");
        tvSummary.setText("Patient Specific Genotype Results and Comprehensive Drug Information for");

        zlvChart = (ZoomRecyclerView) findViewById(R.id.zlvChart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        zlvChart.setLayoutManager(linearLayoutManager);
        zlvChart.setHasFixedSize(true);

        recyclerViewAdapter = new RecyclerViewAdapterPatientSpecificDrugInformation(listOfData);
        if (zlvChart != null) {
            zlvChart.setAdapter(recyclerViewAdapter);
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            zlvChart.setMaxHeight(478);
        } else {
            zlvChart.setMaxHeight(232);
        }

        //adding dividers of RecyclerView
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.divider);
        RecyclerViewDividerDecorator recyclerViewDividerDecorator = new RecyclerViewDividerDecorator(divider);
        zlvChart.addItemDecoration(recyclerViewDividerDecorator);

        //adding header of RecyclerView
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View header = layoutInflater.inflate(R.layout.header_patient_specific_drug_information, zlvChart, false);
        RecyclerViewHeaderDecorator recyclerViewHeaderDecorator = new RecyclerViewHeaderDecorator(header);
        zlvChart.addItemDecoration(recyclerViewHeaderDecorator);

        //allows for diagonal scrolling
        diagonalHorizontalScrollView = (DiagonalHorizontalScrollView) findViewById(R.id.diagonalHorizontalScrollView);
        diagonalHorizontalScrollView.setZoomRecyclerView(zlvChart);

        zlvChart.setRecyclerViewHeaderDecorator(recyclerViewHeaderDecorator);

        int element;
        String tempSuggestion = null;
        String stringInRow;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listOfData.size(); i++) {
            stringInRow = listOfData.get(i);
            element = 1;
            sb.setLength(0);
            for (int j = 0; j < stringInRow.length(); j++) {
                String s = Character.toString(stringInRow.charAt(j));

                //each field is separated by a tab (\t), so StringBuilder is reset every time it detects a tab
                if (s.equals("\t")) {
                    switch (element) {
                        case 4:
                            //replaces all special characters for search suggestions
                            for (int k = 0; k < sb.length(); k++) {
                                if (sb.charAt(k) == ')'
                                        || sb.charAt(k) == '?') {
                                    sb.deleteCharAt(k);
                                    k--;
                                }
                            }
                            tempSuggestion = sb.toString();
                            break;
                        default:
                            break;
                    }
                    String temp2Suggestion;
                    sb.setLength(0);
                    if (tempSuggestion != null) {
                        for (int l = 0; l < tempSuggestion.length(); l++) {
                            if (Character.isUpperCase(tempSuggestion.charAt(l))) {
                                if (l > 1) {
                                    if (tempSuggestion.charAt(l - 2) == ','
                                            || tempSuggestion.charAt(l - 1) == '('
                                            || tempSuggestion.charAt(l - 1) == '/') {
                                        temp2Suggestion = sb.toString()
                                                .replace(",", "")
                                                .replace("(", "")
                                                .replace("/", "")
                                                .trim();
                                        sb.setLength(0);

                                        if (!suggestions.contains(temp2Suggestion) && temp2Suggestion.length() > 1) {
                                            suggestions.add(temp2Suggestion);
                                        }
                                    }
                                }

                                sb.append(tempSuggestion.charAt(l));
                            } else {
                                sb.append(tempSuggestion.charAt(l));
                            }
                        }
                    }

                    temp2Suggestion = sb.toString().trim();
                    if (!suggestions.contains(temp2Suggestion) && temp2Suggestion.length() > 1) {
                        suggestions.add(temp2Suggestion);
                    }


                    sb.setLength(0); //resets the StringBuilder to have nothing in it for next element
                    element++; //moves on to next element
                } else {
                    sb.append(s); //adds the letter onto the end to form the complete term
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        final MenuItem menuItem = menu.findItem(R.id.menuSearch);
        final SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setMaxWidth(Integer.MAX_VALUE);  //makes the searchview take up the entire display horizontally
        searchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //updates search suggestions when user types
        String[] columns = new String[]{"_id", "name"};
        String[] temp = new String[2];
        MatrixCursor cursor = new MatrixCursor(columns);
        int id = 0;
        Collections.sort(suggestions, String.CASE_INSENSITIVE_ORDER);
        for (String s : suggestions) {
            temp[0] = Integer.toString(id++);
            temp[1] = s;
            cursor.addRow(temp);
        }

        simpleCursorAdapter = new MySimpleCursorAdapter(
                PatientSpecificDrugInformation.this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{"name"},
                new int[]{android.R.id.text1},
                android.support.v4.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        searchView.setSuggestionsAdapter(simpleCursorAdapter);


        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                MatrixCursor cursor = (MatrixCursor) searchView.getSuggestionsAdapter().getCursor();
                String suggestion = cursor.getString(1); //2 is the index of col containing suggestion name.
                //searchView.setIconified(true);
                searchView.setQuery(suggestion, false);//setting suggestion
                return true;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) { //when the user presses the enter button
                newText = newText.toLowerCase();
                ArrayList<String> newList = new ArrayList<>();

                StringBuilder sb = new StringBuilder();
                int columnNum;
                if (!newText.isEmpty()) {
                    for (String data : listOfData) {
                        sb.setLength(0);
                        columnNum = 1;
                        for (int i = 0; i < data.length(); i++) {
                            if (columnNum == 9) {
                                break;
                            } else if (data.charAt(i) == '\t'){
                                columnNum++;
                                sb.append(' ');
                            } else if (columnNum > 2) { //The therapeutic column, and words that represent images are not included in search
                                sb.append(data.charAt(i));
                            }
                        }
                        if (sb.toString().toLowerCase().contains(newText)) {
                            newList.add(data);
                        }
                    }
                    recyclerViewAdapter = new RecyclerViewAdapterPatientSpecificDrugInformation(newList);
                    zlvChart.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.setHighlightedText(newText);
                } else {
                    recyclerViewAdapter = new RecyclerViewAdapterPatientSpecificDrugInformation(listOfData);
                    zlvChart.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.setHighlightedText("");
                }

                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //any time the search text has changed
                newText = newText.toLowerCase();
                ArrayList<String> newList = new ArrayList<>();

                StringBuilder sb = new StringBuilder();
                int columnNum;
                if (!newText.isEmpty()) {
                    for (String data : listOfData) {
                        sb.setLength(0);
                        columnNum = 1;
                        for (int i = 0; i < data.length(); i++) {
                            if (columnNum == 9) {
                                break;
                            } else if (data.charAt(i) == '\t'){
                                columnNum++;
                                sb.append(' ');
                            } else if (columnNum > 2) { //The therapeutic column, and words that represent images are not included in search
                                sb.append(data.charAt(i));
                            }
                        }
                        if (sb.toString().toLowerCase().contains(newText)) {
                            newList.add(data);
                        }
                    }
                    recyclerViewAdapter = new RecyclerViewAdapterPatientSpecificDrugInformation(newList);
                    zlvChart.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.setHighlightedText(newText);
                } else {
                    recyclerViewAdapter = new RecyclerViewAdapterPatientSpecificDrugInformation(listOfData);
                    zlvChart.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.setHighlightedText("");
                }

                //updates suggestions based on what user has entered
                String[] columns = new String[]{"_id", "name"};
                String[] temp = new String[2];
                MatrixCursor tempCursor = new MatrixCursor(columns);
                int id = 0;
                Collections.sort(suggestions, String.CASE_INSENSITIVE_ORDER);
                for (String s : suggestions) {
                    if (s.toLowerCase().startsWith(newText)) {
                        temp[0] = Integer.toString(id);
                        temp[1] = s;
                        tempCursor.addRow(temp);
                    }
                    id++;
                }
                simpleCursorAdapter.changeCursor(tempCursor);
                simpleCursorAdapter.notifyDataSetChanged();

                return true;
            }
        });

        return true;
    }
}

