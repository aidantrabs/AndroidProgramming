package com.wlu.aidan.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    protected final String ACTIVITY_NAME = this.getClass().getSimpleName();
    protected final static String PREFERENCES_NAME = "myPrefs";
    protected final static String regexEmail = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String savedEmail;
    private boolean validEmail;
    private EditText editLogin;
    private EditText password;
    private Button loginButton;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "onCreate()");

        editLogin = findViewById(R.id.editLogin);
        password = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.loginButton);

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        savedEmail = sharedPreferences.getString("DefaultEmail", "email@domain.com");
        editLogin.setText(savedEmail);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editLogin.getText().toString();
                editor = sharedPreferences.edit();

                Pattern regexPattern = Pattern.compile(regexEmail, Pattern.CASE_INSENSITIVE);
                Matcher matcherEmail = regexPattern.matcher(email);

                validEmail = matcherEmail.matches();

                if(!validEmail) {
                    return;
                }

                editor.putString("DefaultEmail", email);
                editor.apply();

                if(password.getText().toString().isEmpty()) {
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(ACTIVITY_NAME, "onStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(ACTIVITY_NAME, "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);
        Log.i(ACTIVITY_NAME, "onRestoreInstanceState()");
    }
}