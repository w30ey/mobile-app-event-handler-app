package com.example.localeventhub.activities;

import android.content.Intent;
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

public class FavoritesActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        
        setTitle("My Favorite Events");
        initializeViews();
        setupViewModel();
    }
    
    private void initializeViews() {
        listView = findViewById(R.id.listViewFavorites);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = adapter.getItem(position);
            Toast.makeText(this, "Opening: " + selectedItem, Toast.LENGTH_SHORT).show();
        });
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        
        eventViewModel.getFavoriteEvents().observe(this, events -> {
            if (events != null && !events.isEmpty()) {
                List<String> eventStrings = new ArrayList<>();
                for (Event event : events) {
                    String eventString = "❤️ " + event.getTitle() + 
                                       "\n📅 " + event.getDate() + 
                                       " | 🏘️ " + event.getDistrict();
                    eventStrings.add(eventString);
                }
                
                adapter.clear();
                adapter.addAll(eventStrings);
                adapter.notifyDataSetChanged();
                
                Toast.makeText(this, "Found " + events.size() + " favorite events", Toast.LENGTH_SHORT).show();
            } else {
                adapter.clear();
                adapter.add("No favorite events yet. Add some events to your favorites!");
                adapter.notifyDataSetChanged();
            }
        });
    }
}