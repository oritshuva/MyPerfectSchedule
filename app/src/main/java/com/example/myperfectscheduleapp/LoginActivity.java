package com.example.myperfectscheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    WeeklyScheduleActivity.class
            );

            startActivity(intent);
        });

        btnSignUp.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    SignUpActivity.class
            );

            startActivity(intent);
        });
    }
}