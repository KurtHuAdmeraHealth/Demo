package com.example.andrew.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PGxOnePlusMenu extends AppCompatActivity {

    Button btnViewReport;
    Button btnPatientSpecificDrugInformation;
    Button btnPortablePatientTable;
    Button btnCurrentMedicationInformation;
    Button btnCurrentDrugInteractions;
    Button btnGenotypeAndPhenotypeResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgx_one_plus_menu);

        btnViewReport = (Button) findViewById(R.id.btnViewFullReport);
        btnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PGxOnePlusMenu.this, PDFActivity.class);
                startActivity(i);
            }
        });

        btnPatientSpecificDrugInformation = (Button) findViewById(R.id.btnPatientSpecificDrugInformation);
        btnPatientSpecificDrugInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PGxOnePlusMenu.this, PatientSpecificDrugInformation.class);
                startActivity(i);
            }
        });

        btnPortablePatientTable = (Button) findViewById(R.id.btnPortablePatientTable);
        btnPortablePatientTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PGxOnePlusMenu.this, PortablePatientTable.class);
                startActivity(i);
            }
        });

        btnCurrentMedicationInformation = (Button) findViewById(R.id.btnCurrentMedicationInformation);
        btnCurrentMedicationInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PGxOnePlusMenu.this, CurrentMedicationInformation.class);
                startActivity(i);
            }
        });

        btnCurrentDrugInteractions = (Button) findViewById(R.id.btnCurrentDrugInteractions);
        btnCurrentDrugInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PGxOnePlusMenu.this, CurrentDrugInteractions.class);
                startActivity(i);
            }
        });

        btnGenotypeAndPhenotypeResults = (Button) findViewById(R.id.btnGenotypeAndPhenotypeResults);
        btnGenotypeAndPhenotypeResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PGxOnePlusMenu.this, GenotypeAndPhenotypeResults.class);
                startActivity(i);
            }
        });
    }
}
