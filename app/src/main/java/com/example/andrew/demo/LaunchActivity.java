package com.example.andrew.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.example.andrew.demo.LoginActivity.userID;

public class LaunchActivity extends AppCompatActivity {
    ImageButton imgbtnpatient;
    ImageButton imgbtndoctor;
    SharedPreferences prevuser;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        prevuser = getSharedPreferences("StoUser", Context.MODE_PRIVATE);
        editor = prevuser.edit();
        if (prevuser.getString("alreadyin", null) == null){

        } else {
            userID = Integer.parseInt(prevuser.getString("alreadyin", null));
            Intent skiploginyoufool = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(skiploginyoufool);
        }
        imgbtnpatient = (ImageButton) findViewById(R.id.btnpatient);
        imgbtnpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginyoufool = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(loginyoufool);
            }
        });
        imgbtndoctor = (ImageButton) findViewById(R.id.btndoctor);
        imgbtndoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplication(), "Doctor Option is not Implemented.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
