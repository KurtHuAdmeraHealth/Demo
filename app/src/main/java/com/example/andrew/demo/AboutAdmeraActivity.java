package com.example.andrew.demo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AboutAdmeraActivity extends AppCompatActivity {
    TextView learnmore;
    ImageView backbuttonimg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_admera);

        learnmore = (TextView) findViewById(R.id.txtlearnmorelink);
        String string = learnmore.getText().toString();
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3F51B5")), 0, string.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), 0, string.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), 0, string.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        learnmore.setText(spannableString);
        learnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendtoadmerahealth = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.admerahealth.com/"));
                if (sendtoadmerahealth != null && isIntentAvailable(sendtoadmerahealth)) {
                    startActivity(sendtoadmerahealth);
                }
            }
        });
    }

    public boolean isIntentAvailable(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return (activities.size() > 0);
    }
}
