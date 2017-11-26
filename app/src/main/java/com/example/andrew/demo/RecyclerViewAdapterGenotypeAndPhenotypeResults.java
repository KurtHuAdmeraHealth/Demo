package com.example.andrew.demo;
//Created by Kurt on 7/22/2017.


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterGenotypeAndPhenotypeResults
        extends RecyclerView.Adapter<RecyclerViewAdapterGenotypeAndPhenotypeResults.ViewHolder> {

    private ArrayList<String> listOfData;
    private String highlightedText = "";
    private static final String TAG = RecyclerViewAdapterGenotypeAndPhenotypeResults.class.getSimpleName();
    private static final int highlightColor = Color.parseColor("#80aaff");

    RecyclerViewAdapterGenotypeAndPhenotypeResults(ArrayList<String> listOfData) {
        this.listOfData = listOfData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_genotype_phenotype, parent, false);
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int minHeight = Math.round(35 * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        view.setMinimumHeight(minHeight);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String stringInRow = listOfData.get(position);

        //getting characters from array and appending it to the end
        StringBuilder sb = new StringBuilder();

        //int to keep track of which item the for loop is on
        int element = 1;

        //string values of each of the views
        String tvGeneString = null;
        String tvGenotypeString = null;
        String tvPhenotypeString = null;


        for (int i = 0; i < stringInRow.length(); i++) {
            String s = Character.toString(stringInRow.charAt(i));

            //each field is separated by a tab (\t), so StringBuilder is reset every time it detects a tab
            if (s.equals("\t")) {
                switch (element) {
                    case 6:
                        tvGeneString = sb.toString();
                        break;
                    case 7:
                        tvGenotypeString = sb.toString();
                        break;
                    case 8:
                        tvPhenotypeString = sb.toString();
                        break;
                }
                sb.setLength(0); //resets the StringBuilder to have nothing in it for next element
                element++; //moves on to next element
            } else {
                sb.append(s); //adds the letter onto the end to form the complete term
            }
        }

        //Highlights searched text
        String lowercasePlaceholder;
        boolean moreHighlight;
        int index;
        if (!highlightedText.isEmpty()) {//first checks if highlightedText is empty or not.

            //converts everything to lowercase for accurate comparison
            lowercasePlaceholder = tvGeneString.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {
                SpannableString spannable = new SpannableString(tvGeneString);

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvGene.setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                holder.tvGene.setText(tvGeneString);
            }

            lowercasePlaceholder = tvGenotypeString.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {
                SpannableString spannable = new SpannableString(tvGenotypeString);

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvGenotype.setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                holder.tvGenotype.setText(tvGenotypeString);
            }

            lowercasePlaceholder = tvPhenotypeString.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {
                SpannableString spannable = new SpannableString(tvPhenotypeString);

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvPhenotype.setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                holder.tvPhenotype.setText(tvPhenotypeString);
            }
        } else { //If no searched text, TextViews are set to default values
            holder.tvGene.setText(tvGeneString);
            holder.tvGenotype.setText(tvGenotypeString);
            holder.tvPhenotype.setText(tvPhenotypeString);
        }
    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGene;
        TextView tvGenotype;
        TextView tvPhenotype;

        public ViewHolder(View v) {
            super(v);
            tvGene = v.findViewById(R.id.tvGene);
            tvGenotype = v.findViewById(R.id.tvGenotype);
            tvPhenotype = v.findViewById(R.id.tvPhenotype);
        }
    }

    public void setHighlightedText(String highlightedText) {
        //sets member variable to the search input from user, to be highlighted
        this.highlightedText = highlightedText;
    }
}