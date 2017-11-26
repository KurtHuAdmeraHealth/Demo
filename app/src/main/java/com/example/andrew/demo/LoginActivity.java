package com.example.andrew.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    ArrayList<String> usernames = new ArrayList<>(2);
    ArrayList<String> passwords = new ArrayList<>(2);
    ArrayList<String> untaguser = new ArrayList<>(2);
    ArrayList<String> untagpass = new ArrayList<>(2);
    ArrayList<String> suggestuser = new ArrayList<>(2);
    int randomsixtyone;
    int randomtagsize;
    int d;
    int c;
    int b;
    int a;
    int kickuser = 4;
    boolean logInSuccess = false;
    final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    final String alphabetcryptor = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()-+_=~`/<>?/|";
    Random randomtag = new Random();
    ArrayList<Character> alreadyused = new ArrayList<>(0);
    int h;
    int j;
    int k;
    String getCookie;
    String assemblecrypt = null;
    char characterinquestionnumbertwo = 5;
    int randomgennumone;
    public static int userID;
    String reconstructPrevUser;
    char letterPrevCheckOn;
    SharedPreferences prevuser;
    SharedPreferences.Editor editor;
    ImageView imDrop;
    AutoCompleteTextView edit_User;
    EditText edit_Pass;
    String usernameinquestion;
    char letterinquestion;
    String rebuildinfo = null;
    String tag = null;
    Random tagrandom = new Random();
    String usernameinput;
    String passwordinput;
    char letterinquestiontwo;
    String rebuildinginfo = null;

    String wordinquestiontwo;
    String unbuildtag = null;

//    final SharedPreferences prevuser = getSharedPreferences("StoUser", Context.MODE_PRIVATE);
//    SharedPreferences.Editor editor = prevuser.edit();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imDrop = (ImageView) findViewById(R.id.drop_down_arrow1);
        edit_User = (AutoCompleteTextView) findViewById(R.id.editusername);
        edit_Pass = (EditText) findViewById(R.id.editText2);
        usernames.add("Kurt");
        usernames.add("Zhuosheng");
        usernames.add("Ryan");
        usernames.add("Andrew");
        usernames.add("Jill");
        usernames.add("Amanda");
        usernames.add("Annie");

        passwords.add("hu");
        passwords.add("gu");
        passwords.add("king");
        passwords.add("cheng");
        passwords.add("smith");
        passwords.add("hildebrandt");
        passwords.add("zhou");

        suggestuser.clear();

        prevuser = getSharedPreferences("StoUser", Context.MODE_PRIVATE);
        editor = prevuser.edit();

        if (prevuser.getString("username", null) == null) {
            Log.e(TAG, "Your data is null");
            editor.putString("username", "O");
            editor.apply();
        }

        getCookie = prevuser.getString("username", null);
        Log.e(TAG, getCookie);

        if (!Objects.equals(getCookie, "O")) {
            h = 0;
            while (h < getCookie.length()) {
                j = 0;
                k = 0;
                while (j == 0 && h < getCookie.length()) {
                    letterPrevCheckOn = getCookie.charAt(h);
                    if (letterPrevCheckOn == 'O') {
                        j = 1;
                        suggestuser.add(usernames.get(Integer.parseInt(reconstructPrevUser)));
                    } else {
                        if (k == 0) {
                            k = 1;
                            reconstructPrevUser = Character.toString(letterPrevCheckOn);
                        } else {
                            reconstructPrevUser = reconstructPrevUser + Character.toString(letterPrevCheckOn);
                        }
                    }
                    h++;
                }
            }
        } else {

        }


        ArrayAdapter<String> prevUserAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestuser);
        edit_User.setAdapter(prevUserAdapter);
        edit_User.setThreshold(1);

        imDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_User.showDropDown();
            }
        });

        for (h = alreadyused.size() - 1; h >= 0; h--) {
            alreadyused.remove(h);
        }
        //generate a new crypt
        for (h = 1; h <= 64; h++) {
            j = 0;
            while (j == 0) {
                randomgennumone = randomtag.nextInt(alphabetcryptor.length());
                characterinquestionnumbertwo = alphabetcryptor.charAt(randomgennumone);
                if (!alreadyused.contains(characterinquestionnumbertwo)) {
                    j = 1;
                }
            }
            if (h == 1) {
                assemblecrypt = Character.toString(characterinquestionnumbertwo);
            } else {
                assemblecrypt = assemblecrypt + Character.toString(characterinquestionnumbertwo);
            }
            alreadyused.add(characterinquestionnumbertwo);
        }
        Log.e(TAG, "Crypt" + assemblecrypt);

        for (c = 0; c < usernames.size(); c++) {
            usernameinquestion = usernames.get(c);
            for (b = 0; b < usernames.get(c).length(); b++) {
                letterinquestion = usernameinquestion.charAt(b);
                for (a = 0; a < 62; a++) {
                    if (letterinquestion == alphabet.charAt(a)) {
                        if (b == 0) {
                            rebuildinfo = Character.toString(assemblecrypt.charAt(a));
                        } else {
                            rebuildinfo = rebuildinfo + assemblecrypt.charAt(a);
                        }
                        a = 65;
                    }
                }
            }
            randomtagsize = tagrandom.nextInt(100) + 50;
            for (d = 0; d < (randomtagsize - rebuildinfo.length()); d++) {
                randomsixtyone = tagrandom.nextInt(62);
                if (d == 0) {
                    tag = Character.toString(assemblecrypt.charAt(randomsixtyone));
                } else {
                    tag = tag + assemblecrypt.charAt(randomsixtyone);
                }
            }
            usernames.set((c), rebuildinfo + "¥" + tag);
            Log.e(TAG, "Username " + (c) + ": " + rebuildinfo + "¥" + tag);
        }
        for (c = 0; c < usernames.size(); c++) {
            usernameinquestion = passwords.get(c);
            for (b = 0; b < passwords.get(c).length(); b++) {
                letterinquestion = usernameinquestion.charAt(b);
                for (a = 0; a < 62; a++) {
                    if (letterinquestion == alphabet.charAt(a)) {
                        if (b == 0) {
                            rebuildinfo = Character.toString(assemblecrypt.charAt(a));
                        } else {
                            rebuildinfo = rebuildinfo + assemblecrypt.charAt(a);
                        }
                        a = 65;
                    }
                }
            }
            randomtagsize = tagrandom.nextInt(100) + 50;
            for (d = 0; d < (randomtagsize - rebuildinfo.length()); d++) {
                randomsixtyone = tagrandom.nextInt(62);
                if (d == 0) {
                    tag = Character.toString(assemblecrypt.charAt(randomsixtyone));
                } else {
                    tag = tag + assemblecrypt.charAt(randomsixtyone);
                }
            }
            passwords.set((c), rebuildinfo + "Ω" + tag);
            Log.e(TAG, "Password " + (c) + ": " + rebuildinfo + "Ω" + tag);
        }

        final Button btn_log = (Button) findViewById(R.id.btnLogIn);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                for (b = 0; b < edit_User.getText().toString().length(); b++) {
                    letterinquestiontwo = edit_User.getText().toString().charAt(b);
                    for (a = 0; a < 62; a++) {
                        if (letterinquestiontwo == alphabet.charAt(a)) {
                            if (b == 0) {
                                rebuildinginfo = Character.toString(assemblecrypt.charAt(a));
                            } else {
                                rebuildinginfo = rebuildinginfo + assemblecrypt.charAt(a);
                            }
                            a = 65;
                        }
                    }
                }

                usernameinput = rebuildinginfo;
                for (b = 0; b < edit_Pass.getText().toString().length(); b++) {
                    letterinquestiontwo = edit_Pass.getText().toString().charAt(b);
                    for (a = 0; a < 62; a++) {
                        if (letterinquestiontwo == alphabet.charAt(a)) {
                            if (b == 0) {
                                rebuildinginfo = Character.toString(assemblecrypt.charAt(a));
                            } else {
                                rebuildinginfo = rebuildinginfo + assemblecrypt.charAt(a);
                            }
                            a = 65;
                        }
                    }
                }
                passwordinput = rebuildinginfo;
                for (c = 0; c < usernames.size(); c++) {
                    wordinquestiontwo = usernames.get(c);
                    for (b = 0; b < wordinquestiontwo.length(); b++) {
                        if (wordinquestiontwo.charAt(b) == '¥') {
                            b = wordinquestiontwo.length();
                            untaguser.add(unbuildtag);
                        } else {
                            if (b == 0) {
                                unbuildtag = Character.toString(wordinquestiontwo.charAt(0));
                            } else {
                                unbuildtag = unbuildtag + Character.toString(wordinquestiontwo.charAt(b));
                            }
                        }
                    }
                }
                for (c = 0; c < usernames.size(); c++) {
                    wordinquestiontwo = passwords.get(c);
                    for (b = 0; b < wordinquestiontwo.length(); b++) {
                        if (wordinquestiontwo.charAt(b) == 'Ω') {
                            b = wordinquestiontwo.length();
                            untagpass.add(unbuildtag);
                        } else {
                            if (b == 0) {
                                unbuildtag = Character.toString(wordinquestiontwo.charAt(0));
                            } else {
                                unbuildtag = unbuildtag + Character.toString(wordinquestiontwo.charAt(b));
                            }
                        }

                    }
                }
                for (c = 0; c < usernames.size(); c++) {
                    if (untaguser.get(c).equals(usernameinput) && untagpass.get(c).equals(passwordinput)) {
                        logInSuccess = true;
                        userID = c;
                        c = usernames.size();
                    } else {
                        logInSuccess = false;
                    }
                }
                if (logInSuccess && kickuser >= 0) {
                    if (Objects.equals(getCookie, "O")) {
                        prevuser.edit().remove("username").apply();
                        editor.putString("username", Integer.toString(userID) + "O");
                        editor.apply();
                    } else {
                        if (!suggestuser.contains(edit_User.getText().toString())) {
                            prevuser.edit().remove("username").apply();
                            editor.putString("username", getCookie + Integer.toString(userID) + "O");
                            editor.apply();
                        }
                    }
                    CheckBox rememberme;
                    rememberme = (CheckBox) findViewById(R.id.cbrememberme);
                    if (rememberme.isChecked()) {
                        editor.putString("alreadyin", Integer.toString(userID));
                        editor.apply();
                    }
                    getCookie = prevuser.getString("username", null);
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplication(), "Log In successful.", Toast.LENGTH_SHORT).show();
                } else {
                    kickuser--;
                    if (kickuser < 0) {
                        Toast.makeText(getApplication(), "Client has been locked", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "Username or Password is incorrect, try again.", Toast.LENGTH_SHORT).show();
                    }
                }
                untaguser.clear();
                untagpass.clear();

            }
        });
    }
}
