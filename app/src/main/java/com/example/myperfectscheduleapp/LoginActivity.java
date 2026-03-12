package com.example.myperfectscheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // 1. הצהרת משתנים עבור כל הרכיבים
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // טעינת העיצוב מה-XML

        // 2. אתחול Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 3. קישור המשתנים לרכיבים מה-XML לפי ה-ID שלהם
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        // 4. הגדרת מאזין ללחיצה על כפתור ההתחברות
        btnLogin.setOnClickListener(v -> loginUser());

        // 5. הגדרת מאזין ללחיצה על הטקסט להרשמה
        tvSignUp.setOnClickListener(v -> {
            // מעבר למסך ההרשמה (SignUpActivity)
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // בדיקה: אם המשתמש כבר מחובר, העבר אותו ישר למסך הראשי
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            goToMain();
        }
    }

    private void loginUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // בדיקות תקינות קלט
        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Email is required.");
            editEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Password is required.");
            editPassword.requestFocus();
            return;
        }

        // ביצוע התחברות עם Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // ההתחברות הצליחה
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            goToMain();
                        } else {
                            // ההתחברות נכשלה, הצג הודעת שגיאה
                            Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void goToMain() {
        // פונקציית עזר למעבר למסך הראשי
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        // מחיקת המסכים הקודמים מה-stack, כדי שהמשתמש לא יחזור למסך ההתחברות עם כפתור "אחורה"
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}