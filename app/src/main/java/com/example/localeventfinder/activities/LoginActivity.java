package com.example.localeventfinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localeventfinder.R;

public class LoginActivity extends AppCompatActivity {
    
    private EditText editTextUsername, editTextPassword;
    private RadioGroup radioGroupRole;
    private Button buttonLogin;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initializeViews();
        setupLoginButton();
    }
    
    private void initializeViews() {
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        radioGroupRole = findViewById(R.id.radio_group_role);
        buttonLogin = findViewById(R.id.button_login);
    }
    
    private void setupLoginButton() {
        buttonLogin.setOnClickListener(v -> attemptLogin());
    }
    
    private void attemptLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Simple validation
        if (username.equals("admin") && password.equals("admin123")) {
            navigateToMainActivity("admin", username);
        } else if (username.equals("user") && password.equals("user123")) {
            navigateToMainActivity("user", username);
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void navigateToMainActivity(String role, String username) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_role", role);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}