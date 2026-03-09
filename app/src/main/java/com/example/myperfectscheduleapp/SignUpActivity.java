package com.example.myperfectscheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnCreateAccount;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        auth = FirebaseAuth.getInstance();

        btnCreateAccount.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Enter email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Enter password");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {

                    Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SignUpActivity.this, WeeklyScheduleActivity.class));
                    finish();

                })
                .addOnFailureListener(e -> {

                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                });
    }
}