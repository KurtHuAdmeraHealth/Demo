package com.example.andrew.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ContactAdmeraActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);



        TextView callmemaybe;
        callmemaybe = (TextView) findViewById(R.id.txtcallmemaybe);
        TextView emailmemaybe;
        emailmemaybe = (TextView) findViewById(R.id.txtemailmemaybe);
        emailmemaybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Email us at clientcare@admerahealth.com. Hold to email directly.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        emailmemaybe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            //make this public void IF possible.
            public boolean onLongClick(View view) {
                //sends an email

                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                //TODO: replace the email to clientcare@admerahealth.com
                intent.setData(Uri.parse("mailto:ClientCare@admerahealth.com")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);
                return true;
            }
        });
        callmemaybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Call us at 1-908-222-0533.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabemail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Email us at clientcare@admerahealth.com. Hold to email directly.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            //make this public void IF possible.
            public boolean onLongClick(View view) {
                //sends an email
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                //TODO: replace the email to clientcare@admerahealth.com
                intent.setData(Uri.parse("mailto:ClientCare@admerahealth.com")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);
                return true;
            }
        });
    }

}

