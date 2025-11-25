package com.example.localeventfinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventfinder.R;
import com.example.localeventfinder.adapters.SimpleEventAdapter;
import com.example.localeventfinder.models.Event;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private SimpleEventAdapter adapter;
    private String userRole;
    private String username;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_simple);
        
        // Get user info
        userRole = getIntent().getStringExtra("user_role");
        username = getIntent().getStringExtra("username");
        
        initializeViews();
        setupRecyclerView();
        
        Toast.makeText(this, "Welcome " + username + "! (" + userRole + ")", Toast.LENGTH_SHORT).show();
    }
    
    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        TextView welcomeText = findViewById(R.id.text_welcome);
        welcomeText.setText("Welcome, " + username + " (" + userRole + ")");
        
        Button buttonLogout = findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(v -> {
            finish();
        });
    }
    
    private void setupRecyclerView() {
        // Create sample events
        List<Event> events = new ArrayList<>();
        events.add(new Event("Java Meetup", "Monthly Java developers meeting", "2024-12-15", "18:00", "Tech Hub", "Technology"));
        events.add(new Event("Music Festival", "Local music festival", "2024-12-20", "16:00", "City Park", "Music"));
        events.add(new Event("Art Show", "Contemporary art exhibition", "2024-12-10", "10:00", "Museum", "Art"));
        
        adapter = new SimpleEventAdapter(events);
        recyclerView.setAdapter(adapter);
    }
}