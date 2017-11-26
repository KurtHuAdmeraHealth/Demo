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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterPortablePatientTable extends RecyclerView.Adapter<RecyclerViewAdapterPortablePatientTable.ViewHolder> {

    private ArrayList<String> listOfData;
    private String highlightedText = "";
    private static final String TAG = RecyclerViewAdapterPortablePatientTable.class.getSimpleName();
    private static final int red = Color.parseColor("#C70000");
    private static final int yellow = Color.parseColor("#F2BE03");
    private static final int green = Color.parseColor("#377C3B");
    private static final int purple = Color.parseColor("#7600B6");
    private static final int blue = Color.parseColor("#3D5D98");
    private static final int highlightColor = Color.parseColor("#80aaff");

    final String CONSIDER_ALTERNATIVES = "CONSIDER ALTERNATIVES";
    final String USE_CAUTION = "USE CAUTION";
    final String NORMAL_RESPONSE_EXPECTED = "NORMAL RESPONSE EXPECTED";
    final String DECREASE_DOSE = "DECREASE DOSE";
    final String INCREASE_DOSE = "INCREASE DOSE";
    final String NORMAL_DOSE = "NORMAL DOSE";

    RecyclerViewAdapterPortablePatientTable(ArrayList<String> listOfData) {
        this.listOfData = listOfData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_full, parent, false);
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
        String tvTherapeuticString = null;
        String ivActionString = null;
        String tvDrugImpactedTitleString = null;
        String tvDrugImpactedTextString = null;
        String tvClinicalInterpretationString = null;
        String tvGeneString = null;
        String tvGenotypeString = null;
        String tvPhenotypeString = null;


        for (int i = 0; i < stringInRow.length(); i++) {
            String s = Character.toString(stringInRow.charAt(i));

            //each field is separated by a tab (\t), so StringBuilder is reset every time it detects a tab
            if (s.equals("\t")) {
                switch (element) {
                    case 1:
                        tvTherapeuticString = sb.toString();
                        break;
                    case 2:
                        ivActionString = sb.toString();
                        break;
                    case 3: //case 3 and 4 are both under "Drug Impacted" (case 3 is title, and case 4 is text)
                        sb.append(":"); //add on a colon in the title
                        tvDrugImpactedTitleString = sb.toString();
                        break;
                    case 4:
                        //replace all "?" with (R) symbol --> unicode is 00AE
                        for (int j = 0; j < sb.length(); j++) {
                            if (sb.charAt(j) == '?') {
                                sb.replace(j, j + 1, "\u00AE");
                            }
                        }
                        tvDrugImpactedTextString = sb.toString();
                        break;
                    case 5:
                        tvClinicalInterpretationString = sb.toString();
                        //reformats views with more than one suggestion
                        if (tvClinicalInterpretationString.contains("or " + CONSIDER_ALTERNATIVES)
                                || tvClinicalInterpretationString.contains("or " + USE_CAUTION)
                                || tvClinicalInterpretationString.contains("or " + NORMAL_RESPONSE_EXPECTED)
                                || tvClinicalInterpretationString.contains("or " + DECREASE_DOSE)
                                || tvClinicalInterpretationString.contains("or " + INCREASE_DOSE)
                                || tvClinicalInterpretationString.contains("or " + NORMAL_DOSE)) {
                            tvClinicalInterpretationString = tvClinicalInterpretationString.replace(" or ", "\n\nOR\n\n");
                        }

                        break;
                    case 6:
                        tvGeneString = sb.toString();
                        break;
                    case 7:
                        tvGenotypeString = sb.toString();
                        break;
                    case 8:
                        tvPhenotypeString = sb.toString();
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



        //The params can be set to different params when there is one image, or two
        LinearLayout.LayoutParams paramsFull = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                2.0f
        );
        LinearLayout.LayoutParams paramsEmpty = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.0f
        );
        LinearLayout.LayoutParams paramsDefault = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1.0f
        );


        //setting images
        if (ivActionString.equals("STOP DOWN")) {
            holder.ivAction1.setLayoutParams(paramsDefault);
            holder.ivAction2.setLayoutParams(paramsDefault);
            holder.ivAction1.setImageResource(R.mipmap.stop_circle);
            holder.ivAction2.setVisibility(View.VISIBLE);
            holder.ivAction2.setImageResource(R.mipmap.decrease_circle);
            holder.ivAction1.setBackgroundColor(red);
            holder.ivAction2.setBackgroundColor(purple);
        } else if (ivActionString.equals("STOP UP")){
            holder.ivAction1.setLayoutParams(paramsDefault);
            holder.ivAction2.setLayoutParams(paramsDefault);
            holder.ivAction1.setImageResource(R.mipmap.stop_circle);
            holder.ivAction2.setVisibility(View.VISIBLE);
            holder.ivAction2.setImageResource(R.mipmap.increase_circle);
            holder.ivAction1.setBackgroundColor(red);
            holder.ivAction2.setBackgroundColor(blue);
        } else if (ivActionString.equals("SLOW")) {
            holder.ivAction2.setVisibility(View.GONE);
            holder.ivAction1.setLayoutParams(paramsFull);
            holder.ivAction1.setLayoutParams(paramsEmpty);
            holder.ivAction1.setImageResource(R.mipmap.caution_circle);
            holder.ivAction1.setBackgroundColor(yellow);
        } else if (ivActionString.equals("GO")) {
            holder.ivAction2.setVisibility(View.GONE);
            holder.ivAction1.setLayoutParams(paramsFull);
            holder.ivAction1.setLayoutParams(paramsEmpty);
            holder.ivAction1.setImageResource(R.mipmap.normal_response_circle);
            holder.ivAction1.setBackgroundColor(green);
        } else if (ivActionString.equals("STOP")) {
            holder.ivAction2.setVisibility(View.GONE);
            holder.ivAction1.setLayoutParams(paramsFull);
            holder.ivAction1.setLayoutParams(paramsEmpty);
            holder.ivAction1.setImageResource(R.mipmap.stop_circle);
            holder.ivAction1.setBackgroundColor(red);
        } else if (ivActionString.equals("DOWN")) {
            holder.ivAction2.setVisibility(View.GONE);
            holder.ivAction1.setLayoutParams(paramsFull);
            holder.ivAction1.setLayoutParams(paramsEmpty);
            holder.ivAction1.setImageResource(R.mipmap.decrease_circle);
            holder.ivAction1.setBackgroundColor(purple);
        } else if (ivActionString.equals("UP")) {
            holder.ivAction2.setVisibility(View.GONE);
            holder.ivAction1.setLayoutParams(paramsFull);
            holder.ivAction1.setLayoutParams(paramsEmpty);
            holder.ivAction1.setImageResource(R.mipmap.increase_circle);
            holder.ivAction1.setBackgroundColor(blue);
        } else {
            Log.e(TAG, "No action string found to set image");
        }


        //SETTING TEXT FOR CLINICAL INTERPRETATION
        final String CONSIDER_ALTERNATIVES = "CONSIDER ALTERNATIVES";
        final String USE_CAUTION = "USE CAUTION";
        final String NORMAL_RESPONSE_EXPECTED = "NORMAL RESPONSE EXPECTED";
        final String DECREASE_DOSE = "DECREASE DOSE";
        final String INCREASE_DOSE = "INCREASE DOSE";
        final String NORMAL_DOSE = "NORMAL DOSE";

        //adds a newline after each keyword
        tvClinicalInterpretationString = tvClinicalInterpretationString.replace((CONSIDER_ALTERNATIVES + " "), (CONSIDER_ALTERNATIVES + "\r\n"))
                .replace((USE_CAUTION + " "), (USE_CAUTION + "\r\n"))
                .replace((NORMAL_RESPONSE_EXPECTED + " "), (NORMAL_RESPONSE_EXPECTED + "\r\n"))
                .replace((DECREASE_DOSE + " "), (DECREASE_DOSE + "\r\n"))
                .replace((INCREASE_DOSE + " "), (INCREASE_DOSE + "\r\n"))
                .replace((NORMAL_DOSE + " "), (NORMAL_DOSE + "\r\n"));

        SpannableString spannableClinicalInterpretation = new SpannableString(tvClinicalInterpretationString);

        if (tvClinicalInterpretationString.contains(CONSIDER_ALTERNATIVES)) { //red

            spannableClinicalInterpretation.setSpan(new ForegroundColorSpan(red), tvClinicalInterpretationString.indexOf(CONSIDER_ALTERNATIVES), (tvClinicalInterpretationString.indexOf(CONSIDER_ALTERNATIVES) + CONSIDER_ALTERNATIVES.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableClinicalInterpretation.setSpan(new StyleSpan(Typeface.BOLD), tvClinicalInterpretationString.indexOf(CONSIDER_ALTERNATIVES), (tvClinicalInterpretationString.indexOf(CONSIDER_ALTERNATIVES) + CONSIDER_ALTERNATIVES.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvClinicalInterpretation.setText(spannableClinicalInterpretation, TextView.BufferType.SPANNABLE);
        }
        if (tvClinicalInterpretationString.contains(USE_CAUTION)) { //yellow

            spannableClinicalInterpretation.setSpan(new ForegroundColorSpan(yellow), tvClinicalInterpretationString.indexOf(USE_CAUTION), (tvClinicalInterpretationString.indexOf(USE_CAUTION) + USE_CAUTION.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableClinicalInterpretation.setSpan(new StyleSpan(Typeface.BOLD), tvClinicalInterpretationString.indexOf(USE_CAUTION), (tvClinicalInterpretationString.indexOf(USE_CAUTION) + USE_CAUTION.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvClinicalInterpretation.setText(spannableClinicalInterpretation, TextView.BufferType.SPANNABLE);
        }
        if (tvClinicalInterpretationString.contains(NORMAL_RESPONSE_EXPECTED)) { //green

            spannableClinicalInterpretation.setSpan(new ForegroundColorSpan(green), tvClinicalInterpretationString.indexOf(NORMAL_RESPONSE_EXPECTED), (tvClinicalInterpretationString.indexOf(NORMAL_RESPONSE_EXPECTED) + NORMAL_RESPONSE_EXPECTED.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableClinicalInterpretation.setSpan(new StyleSpan(Typeface.BOLD), tvClinicalInterpretationString.indexOf(NORMAL_RESPONSE_EXPECTED), (tvClinicalInterpretationString.indexOf(NORMAL_RESPONSE_EXPECTED) + NORMAL_RESPONSE_EXPECTED.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvClinicalInterpretation.setText(spannableClinicalInterpretation, TextView.BufferType.SPANNABLE);
        }
        if (tvClinicalInterpretationString.contains(DECREASE_DOSE)) { //purple

            spannableClinicalInterpretation.setSpan(new ForegroundColorSpan(purple), tvClinicalInterpretationString.indexOf(DECREASE_DOSE), (tvClinicalInterpretationString.indexOf(DECREASE_DOSE) + DECREASE_DOSE.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableClinicalInterpretation.setSpan(new StyleSpan(Typeface.BOLD), tvClinicalInterpretationString.indexOf(DECREASE_DOSE), (tvClinicalInterpretationString.indexOf(DECREASE_DOSE) + DECREASE_DOSE.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvClinicalInterpretation.setText(spannableClinicalInterpretation, TextView.BufferType.SPANNABLE);
        }
        if (tvClinicalInterpretationString.contains(INCREASE_DOSE)) { // blue

            spannableClinicalInterpretation.setSpan(new ForegroundColorSpan(blue), tvClinicalInterpretationString.indexOf(INCREASE_DOSE), (tvClinicalInterpretationString.indexOf(INCREASE_DOSE) + INCREASE_DOSE.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableClinicalInterpretation.setSpan(new StyleSpan(Typeface.BOLD), tvClinicalInterpretationString.indexOf(INCREASE_DOSE), (tvClinicalInterpretationString.indexOf(INCREASE_DOSE) + INCREASE_DOSE.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvClinicalInterpretation.setText(spannableClinicalInterpretation, TextView.BufferType.SPANNABLE);
        }
        if (tvClinicalInterpretationString.contains(NORMAL_DOSE)) { // green

            spannableClinicalInterpretation.setSpan(new ForegroundColorSpan(green), tvClinicalInterpretationString.indexOf(NORMAL_DOSE), (tvClinicalInterpretationString.indexOf(NORMAL_DOSE) + NORMAL_DOSE.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            spannableClinicalInterpretation.setSpan(new StyleSpan(Typeface.BOLD), tvClinicalInterpretationString.indexOf(NORMAL_DOSE), (tvClinicalInterpretationString.indexOf(NORMAL_DOSE) + NORMAL_DOSE.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvClinicalInterpretation.setText(spannableClinicalInterpretation, TextView.BufferType.SPANNABLE);
        }

        //Highlights searched text
        String lowercasePlaceholder;
        boolean moreHighlight;
        int index;
        if (!highlightedText.isEmpty()) {//first checks if highlightedText is empty or not.

            //converts everything to lowercase for accurate comparison
            lowercasePlaceholder = tvTherapeuticString.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {

                //creates a SpannableString, which has the ability to change background color
                SpannableString spannable = new SpannableString(tvTherapeuticString);

                //makes sure every instance of the searched text is highlighted in the string
                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvTherapeutic.setText(spannable, TextView.BufferType.SPANNABLE);
            } else { //if the TextView doesn't contain highlightedText, then it is set to default value
                holder.tvTherapeutic.setText(tvTherapeuticString);
            }

            lowercasePlaceholder = tvDrugImpactedTitleString.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {
                SpannableString spannable = new SpannableString(tvDrugImpactedTitleString);

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvDrugImpactedTitle.setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                holder.tvDrugImpactedTitle.setText(tvDrugImpactedTitleString);
            }

            lowercasePlaceholder = tvDrugImpactedTextString.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {
                SpannableString spannable = new SpannableString(tvDrugImpactedTextString);

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannable.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvDrugImpactedText.setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                holder.tvDrugImpactedText.setText(tvDrugImpactedTextString);
            }

            //uses old spannable used by the ClinicalInterpretation string to avoid overriding the spannable
            lowercasePlaceholder = tvClinicalInterpretationString.toLowerCase();
            if (lowercasePlaceholder.contains(highlightedText)) {

                index = lowercasePlaceholder.indexOf(highlightedText);
                moreHighlight = true;
                do {
                    spannableClinicalInterpretation.setSpan(new BackgroundColorSpan(highlightColor), index, index + highlightedText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    index = lowercasePlaceholder.indexOf(highlightedText, index + 1);
                    if (index == -1) {
                        moreHighlight = false;
                    }
                } while (moreHighlight);

                holder.tvClinicalInterpretation.setText(spannableClinicalInterpretation, TextView.BufferType.SPANNABLE);
            } else {
                //does nothing if no highlight because textView is already set
            }

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
            holder.tvTherapeutic.setText(tvTherapeuticString);
            holder.tvDrugImpactedTitle.setText(tvDrugImpactedTitleString);
            //images will be set below
            holder.tvDrugImpactedText.setText(tvDrugImpactedTextString);
            //text for Clinical Interpretation will be set below
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
        TextView tvTherapeutic;
        ImageView ivAction1;
        ImageView ivAction2;
        TextView tvDrugImpactedTitle;
        TextView tvDrugImpactedText;
        TextView tvClinicalInterpretation;
        TextView tvGene;
        TextView tvGenotype;
        TextView tvPhenotype;

        public ViewHolder(View v) {
            super(v);
            tvTherapeutic = v.findViewById(R.id.tvTherapeutic);
            ivAction1 = v.findViewById(R.id.ivAction1);
            ivAction2 = v.findViewById(R.id.ivAction2);
            tvDrugImpactedTitle = v.findViewById(R.id.tvDrugImpactedTitle);
            tvDrugImpactedTitle.setTypeface(null, Typeface.BOLD); //makes the title bold
            tvDrugImpactedText = v.findViewById(R.id.tvDrugImpactedText);
            tvClinicalInterpretation = v.findViewById(R.id.tvClinicalInterpretation);
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