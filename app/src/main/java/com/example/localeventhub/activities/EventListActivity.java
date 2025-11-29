package com.example.localeventhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.localeventhub.R;

public class EventListActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        
        setTitle("Available Events");
        setupEventList();
    }
    
    private void setupEventList() {
        ListView listView = findViewById(R.id.listViewEvents);
        
        // Sample events data
        String[] events = {
            "Music Festival - Feb 15 - Arada",
            "Art Exhibition - Feb 20 - Arada", 
            "Tech Conference - Mar 1 - Bole",
            "Food Festival - Feb 25 - Bole",
            "Book Fair - Mar 5 - Yeka"
        };
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, events);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedEvent = events[position];
            Toast.makeText(this, "Selected: " + selectedEvent, Toast.LENGTH_SHORT).show();
            
            // Navigate to event details
            Intent intent = new Intent(this, EventDetailActivity.class);
            startActivity(intent);
        });
    }
}