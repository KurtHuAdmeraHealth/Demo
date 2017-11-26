package com.example.andrew.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PDFActivity extends AppCompatActivity {
    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FragmentPDFRenderer(),
                            FRAGMENT_PDF_RENDERER_BASIC)
                    .commit();
        }
    }

}
