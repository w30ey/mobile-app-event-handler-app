package com.example.localeventhub.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.localeventhub.R;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminPanelActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<Event> pendingEventsList = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        
        setTitle("Admin Panel - Pending Events");
        initializeViews();
        setupViewModel();
    }
    
    private void initializeViews() {
        listView = findViewById(R.id.listViewPendingEvents);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < pendingEventsList.size()) {
                Event event = pendingEventsList.get(position);
                showApprovalOptions(event);
            }
        });
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        
        // Observe pending events from database
        eventViewModel.getPendingEvents().observe(this, events -> {
            if (events != null && !events.isEmpty()) {
                pendingEventsList.clear();
                pendingEventsList.addAll(events);
                
                List<String> eventStrings = new ArrayList<>();
                for (Event event : events) {
                    String eventString = event.getTitle() + " - " + 
                                       event.getDate() + " - " + 
                                       event.getDistrict() + " - " +
                                       "Organizer: " + event.getOrganizerId();
                    eventStrings.add(eventString);
                }
                
                adapter.clear();
                adapter.addAll(eventStrings);
                adapter.notifyDataSetChanged();
                
                Toast.makeText(this, "Found " + events.size() + " pending events", Toast.LENGTH_SHORT).show();
            } else {
                adapter.clear();
                adapter.add("No pending events found");
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "No pending events", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void showApprovalOptions(Event event) {
        // Simple approval dialog
        String message = "Approve: " + event.getTitle() + "?\n\n" +
                        "Date: " + event.getDate() + "\n" +
                        "Location: " + event.getLocation() + "\n" +
                        "District: " + event.getDistrict();
        
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        
        // Simulate approval after toast
        new android.os.Handler().postDelayed(() -> {
            event.setApproved(true);
            eventViewModel.update(event);
            Toast.makeText(this, "Event approved: " + event.getTitle(), Toast.LENGTH_SHORT).show();
        }, 2000);
    }
}