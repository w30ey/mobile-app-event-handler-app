package com.example.localeventhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.localeventhub.R;

public class MainActivity extends AppCompatActivity {
    private int userId;
    private String userRole;
    private String username;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getUserDataFromIntent();
        setupUI();
    }
    
    private void getUserDataFromIntent() {
        Intent intent = getIntent();
        userId = intent.getIntExtra("USER_ID", -1);
        userRole = intent.getStringExtra("USER_ROLE");
        username = intent.getStringExtra("USERNAME");
        
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome, " + username + "!\nRole: " + userRole);
        
        // Set activity title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Local Event Hub - " + userRole);
        }
    }
    
    private void setupUI() {
        Button btnAdminPanel = findViewById(R.id.btnAdminPanel);
        Button btnCreateEvent = findViewById(R.id.btnCreateEvent);
        Button btnViewEvents = findViewById(R.id.btnViewEvents);
        Button btnEventDetails = findViewById(R.id.btnEventDetails);
        
        // Show admin button only for admin users
        if ("admin".equals(userRole)) {
            btnAdminPanel.setVisibility(android.view.View.VISIBLE);
            btnAdminPanel.setOnClickListener(v -> {
                Toast.makeText(this, "Admin Panel coming soon!", Toast.LENGTH_SHORT).show();
            });
        } else {
            btnAdminPanel.setVisibility(android.view.View.GONE);
        }
        
        // Show create event button for organizers
        if ("organizer".equals(userRole)) {
            btnCreateEvent.setVisibility(android.view.View.VISIBLE);
            btnCreateEvent.setOnClickListener(v -> {
                Toast.makeText(this, "Create Event coming soon!", Toast.LENGTH_SHORT).show();
            });
        } else {
            btnCreateEvent.setVisibility(android.view.View.GONE);
        }
        
        // All users can view events
        btnViewEvents.setOnClickListener(v -> {
            Intent intent = new Intent(this, EventListActivity.class);
            intent.putExtra("USER_ID", userId);
            intent.putExtra("USER_ROLE", userRole);
            startActivity(intent);
        });
        
        btnEventDetails.setOnClickListener(v -> {
            Intent intent = new Intent(this, EventDetailActivity.class);
            startActivity(intent);
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            // Navigate back to login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}