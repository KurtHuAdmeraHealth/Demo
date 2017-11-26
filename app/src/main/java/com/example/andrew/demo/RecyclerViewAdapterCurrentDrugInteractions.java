package com.example.andrew.demo;
//Created by Kurt on 7/22/2017.


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterCurrentDrugInteractions
        extends RecyclerView.Adapter<RecyclerViewAdapterCurrentDrugInteractions.ViewHolder> {
    private static final String TAG = RecyclerViewAdapterCurrentDrugInteractions.class.getSimpleName();
    private ArrayList<String> listOfData;
    private String highlightedText = "";
    private static final int red = Color.parseColor("#C70000");
    private static final int orange = Color.parseColor("#F6861F");
    private static final int green = Color.parseColor("#377C3B");
    private static final int highlightColor = Color.parseColor("#80aaff");

    final String CONSIDER_ALTERNATIVES = "CONSIDER ALTERNATIVES";
    final String USE_CAUTION = "USE CAUTION";
    final String NORMAL_RESPONSE_EXPECTED = "NORMAL RESPONSE EXPECTED";
    final String DECREASE_DOSE = "DECREASE DOSE";
    final String INCREASE_DOSE = "INCREASE DOSE";
    final String NORMAL_DOSE = "NORMAL DOSE";

    RecyclerViewAdapterCurrentDrugInteractions(ArrayList<String> listOfData) {
        this.listOfData = listOfData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_drug_interactions, parent, false);
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int minHeight = Math.round(95 * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
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
        String ivSeverity = null;
        String tvDrugs = null;
        String tvWarning = null;
        String tvDocumentation = null;
        String tvClinicalManagement = null;


        for (int i = 0; i < stringInRow.length(); i++) {
            String s = Character.toString(stringInRow.charAt(i));

            //each field is separated by a tab (\t), so StringBuilder is reset every time it detects a tab
            if (s.equals("\t")) {
                switch (element) {
                    case 2:
                        ivSeverity = sb.toString();
                        break;
                    case 3:
                        tvDrugs = sb.toString();
                        break;
                    case 4:
                        tvDrugs = tvDrugs + " -- " + sb.toString();
                    case 5:
                        tvWarning = sb.toString();
                        break;
                    case 6:
                        tvDocumentation = sb.toString();
                        //reformats views with more than one suggestion
                        if (tvDocumentation.contains("or " + CONSIDER_ALTERNATIVES)
                                || tvDocumentation.contains("or " + USE_CAUTION)
                                || tvDocumentation.contains("or " + NORMAL_RESPONSE_EXPECTED)
                                || tvDocumentation.contains("or " + DECREASE_DOSE)
                                || tvDocumentation.contains("or " + INCREASE_DOSE)
                                || tvDocumentation.contains("or " + NORMAL_DOSE)) {
                            tvDocumentation = tvDocumentation.replace(" or ", "\n\nOR\n\n");
                        }
                        break;
                    case 7:
                        //replace all "?" with (R) symbol --> unicode is 00AE
                        for (int j = 0; j < sb.length(); j++) {
                            if (sb.charAt(j) == '?') {
                                sb.replace(j, j + 1, "\u00AE");
                            }
                        }
                        tvClinicalManagement = sb.toString();
                        break;
                    default:
                        break;
                }
                sb.setLength(0); //resets the StringBuilder to have nothing in it for next element
                element++; //moves on to next element
            } else {
                sb.append(s); //adds the letter onto the end to form the complete term
            }
        }

        //TODO: Replace with actual images and names
        //setting images
        if (ivSeverity.equals("MAJOR")) {
            holder.ivSeverity.setImageResource(R.mipmap.major);
        } else if (ivSeverity.equals("MODERATE")){
            holder.ivSeverity.setImageResource(R.mipmap.moderate);
        } else if (ivSeverity.equals("CONTRAINDICATED")) {
            holder.ivSeverity.setImageResource(R.mipmap.contraindicated);
        } else {
            Log.e(TAG, "No action string found to set image");
        }


        //setting text for Warning title
        final String MAJOR = "MAJOR";
        final String MODERATE = "MODERATE";
        final String CONTRAINDICATED = "CONTRAINDICATED";
        tvWarning = ivSeverity + " " + tvWarning;
        //adds a newline after each keyword
        tvWarning = tvWarning.replace((MAJOR + " "), (MAJOR + "\r\n"))
                .replace((MODERATE + " "), (MODERATE + "\r\n"))
                .replace((CONTRAINDICATED + " "), (CONTRAINDICATED + "\r\n"));

        SpannableString spannableWarning = new SpannableString(tvWarning);

        if (tvWarning.contains(MAJOR)) { //red

            spannableWarning.setSpan(new ForegroundColorSpan(red), tvWarning.indexOf(MAJOR), (tvWarning.indexOf(MAJOR) + MAJOR.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableWarning.setSpan(new StyleSpan(Typeface.BOLD), tvWarning.indexOf(MAJOR), (tvWarning.indexOf(MAJOR) + MAJOR.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvWarning.setText(spannableWarning, TextView.BufferType.SPANNABLE);
        }
        if (tvWarning.contains(MODERATE)) { //orange

            spannableWarning.setSpan(new ForegroundColorSpan(orange), tvWarning.indexOf(MODERATE), (tvWarning.indexOf(MODERATE) + MODERATE.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableWarning.setSpan(new StyleSpan(Typeface.BOLD), tvWarning.indexOf(MODERATE), (tvWarning.indexOf(MODERATE) + MODERATE.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvWarning.setText(spannableWarning, TextView.BufferType.SPANNABLE);
        }
        if (tvWarning.contains(CONTRAINDICATED)) { //red

            spannableWarning.setSpan(new ForegroundColorSpan(green), tvWarning.indexOf(CONTRAINDICATED), (tvWarning.indexOf(CONTRAINDICATED) + CONTRAINDICATED.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableWarning.setSpan(new StyleSpan(Typeface.BOLD), tvWarning.indexOf(CONTRAINDICATED), (tvWarning.indexOf(CONTRAINDICATED) + CONTRAINDICATED.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvWarning.setText(spannableWarning, TextView.BufferType.SPANNABLE);
        }


        //Highlights searched text
        String lowercasePlaceholder;
        boolean moreHighlight;
        int index;
        if (!highlightedText.isEmpty()) {//first checks if highlightedText is empty or not.

            //converts everything to lowercase for accurate comparison
            lowercasePlaceholder = tvDrugs.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {
                SpannableString spannable = new SpannableString(tvDrugs);

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvDrugs.setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                holder.tvDrugs.setText(tvDrugs);
            }

            lowercasePlaceholder = tvDocumentation.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {
                SpannableString spannable = new SpannableString(tvDocumentation);

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvDocumentation.setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                holder.tvDocumentation.setText(tvDocumentation);
            }

            //uses old spannable used by the tvWarning string to avoid overriding the spannable
            lowercasePlaceholder = tvWarning.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannableWarning.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvWarning.setText(spannableWarning, TextView.BufferType.SPANNABLE);
            } else {
                //does nothing if no highlight because textView is already set
            }

            lowercasePlaceholder = tvClinicalManagement.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {
                SpannableString spannable = new SpannableString(tvClinicalManagement);

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvClinicalManagement.setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                holder.tvClinicalManagement.setText(tvClinicalManagement);
            }

        } else { //If no searched text, TextViews are set to default values
            holder.tvDrugs.setText(tvDrugs);
            //tvWarning text set above
            holder.tvDocumentation.setText(tvDocumentation);
            holder.tvClinicalManagement.setText(tvClinicalManagement);
        }
    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSeverity;
        TextView tvDrugs;
        TextView tvWarning;
        TextView tvDocumentation;
        TextView tvClinicalManagement;

        public ViewHolder(View v) {
            super(v);
            ivSeverity = v.findViewById(R.id.ivSeverity);
            tvDrugs = v.findViewById(R.id.tvDrugs);
            tvWarning = v.findViewById(R.id.tvWarning);
            tvDocumentation = v.findViewById(R.id.tvDocumentation);
            tvClinicalManagement = v.findViewById(R.id.tvClinicalManagement);
        }
    }

    public void setHighlightedText(String highlightedText) {
        //sets member variable to the search input from user, to be highlighted
        this.highlightedText = highlightedText;
    }
}