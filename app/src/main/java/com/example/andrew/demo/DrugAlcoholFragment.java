package com.example.andrew.demo;
//Created by Kurt on 8/28/2017.


import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DrugAlcoholFragment extends Fragment {
    public static final String TAG = DrugDrugFragment.class.getName();
    FragmentZoomRecyclerView zlvChart;
    ArrayList<String> listOfData = new ArrayList<>();
    RecyclerViewAdapterCurrentDrugInteractions recyclerViewAdapter;

    TextView tvName;
    TextView tvSummary;

    FragmentDiagonalHorizontalScrollView fragmentDiagonalHorizontalScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_drug_interactions_drug_alcohol, container, false);

        tvName = rootView.findViewById(R.id.tvName);
        tvSummary = rootView.findViewById(R.id.tvSummary);

        InputStream is = this.getResources().openRawResource(R.raw.input_data);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        int position = 0;
        listOfData.clear();

        try {
            br.readLine(); //skips the first line because those are titles
            while ((line = br.readLine()) != null) {
                if (line.startsWith("DAI")) {
                    listOfData.add(position, line);
                    position++;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "FAILED TO READ FILE");
        }

        //setting the TextViews at the top
        tvName.setText("Smith, John");
        tvSummary.setText("Drug-Alcohol Interactions for");

        zlvChart = rootView.findViewById(R.id.zlvChart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        zlvChart.setLayoutManager(linearLayoutManager);
        zlvChart.setHasFixedSize(true);

        recyclerViewAdapter = new RecyclerViewAdapterCurrentDrugInteractions(listOfData);
        if (zlvChart != null) {
            zlvChart.setAdapter(recyclerViewAdapter);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            zlvChart.setMaxHeight(500);
        } else {
            zlvChart.setMaxHeight(228);
        }

        //adding dividers of RecyclerView
        Drawable divider = ContextCompat.getDrawable(getActivity(), R.drawable.divider);
        RecyclerViewDividerDecorator recyclerViewDividerDecorator = new RecyclerViewDividerDecorator(divider);
        zlvChart.addItemDecoration(recyclerViewDividerDecorator);

        //adding header of RecyclerView
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View header = layoutInflater.inflate(R.layout.header_current_drug_interactions, zlvChart, false);
        FragmentRecyclerViewHeaderDecorator recyclerViewHeaderDecorator = new FragmentRecyclerViewHeaderDecorator(header);
        zlvChart.addItemDecoration(recyclerViewHeaderDecorator);

        //allows for diagonal scrolling
        fragmentDiagonalHorizontalScrollView = rootView.findViewById(R.id.diagonalHorizontalScrollView);
        fragmentDiagonalHorizontalScrollView.setFragmentZoomRecyclerView(zlvChart);

        zlvChart.setRecyclerViewHeaderDecorator(recyclerViewHeaderDecorator);

        return rootView;
    }
}
