package com.example.localeventhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.localeventhub.R;
import com.example.localeventhub.models.User;
import com.example.localeventhub.viewmodels.AuthViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private AuthViewModel authViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initializeViews();
        setupViewModel();
    }
    
    private void initializeViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        
        btnLogin.setOnClickListener(v -> attemptLogin());
        
        // Set demo credentials for testing
        etUsername.setText("user1");
        etPassword.setText("pass123");
    }
    
    private void setupViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        
        authViewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                navigateBasedOnRole(user);
            }
        });
        
        authViewModel.getLoginError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void attemptLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        
        authViewModel.login(username, password);
    }
    
    private void navigateBasedOnRole(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("USER_ID", user.getId());
        intent.putExtra("USER_ROLE", user.getRole());
        intent.putExtra("USERNAME", user.getUsername());
        startActivity(intent);
        finish();
    }
}