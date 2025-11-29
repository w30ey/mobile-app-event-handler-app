package com.example.localeventhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.localeventhub.R;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<Event> eventsList = new ArrayList<>();
    private Spinner spinnerDistrictFilter;
    
    private final String[] DISTRICTS = {"All", "Bole", "Arada", "Yeka", "Nifas Silk"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        
        setTitle("Available Events");
        initializeViews();
        setupViewModel();
        setupDistrictFilter();
    }
    
    private void initializeViews() {
        listView = findViewById(R.id.listViewEvents);
        spinnerDistrictFilter = findViewById(R.id.spinnerDistrictFilter);
        
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < eventsList.size()) {
                Event event = eventsList.get(position);
                Intent intent = new Intent(this, EventDetailActivity.class);
                intent.putExtra("EVENT_ID", event.getId());
                startActivity(intent);
            }
        });
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        loadEvents("All"); // Load all events initially
    }
    
    private void setupDistrictFilter() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, DISTRICTS);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrictFilter.setAdapter(spinnerAdapter);
        
        spinnerDistrictFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrict = DISTRICTS[position];
                loadEvents(selectedDistrict);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    
    private void loadEvents(String district) {
        if ("All".equals(district)) {
            eventViewModel.getAllApprovedEvents().observe(this, events -> {
                updateEventsList(events);
            });
        } else {
            eventViewModel.getEventsByDistrict(district).observe(this, events -> {
                updateEventsList(events);
            });
        }
    }
    
    private void updateEventsList(List<Event> events) {
        if (events != null && !events.isEmpty()) {
            eventsList.clear();
            eventsList.addAll(events);
            
            List<String> eventStrings = new ArrayList<>();
            for (Event event : events) {
                String eventString = "🎉 " + event.getTitle() + 
                                   "\n📅 " + event.getDate() + 
                                   " | 🏘️ " + event.getDistrict() +
                                   (event.isFavorite() ? " ❤️" : "");
                eventStrings.add(eventString);
            }
            
            adapter.clear();
            adapter.addAll(eventStrings);
            adapter.notifyDataSetChanged();
            
            Toast.makeText(this, "Found " + events.size() + " events", Toast.LENGTH_SHORT).show();
        } else {
            adapter.clear();
            adapter.add("No events found in " + (getSelectedDistrict().equals("All") ? "any district" : getSelectedDistrict()));
            adapter.notifyDataSetChanged();
        }
    }
    
    private String getSelectedDistrict() {
        return spinnerDistrictFilter.getSelectedItem().toString();
    }
}