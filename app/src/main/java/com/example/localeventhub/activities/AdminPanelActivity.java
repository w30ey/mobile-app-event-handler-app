package com.example.localeventhub.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.adapters.AdminEventAdapter;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

public class AdminPanelActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private AdminEventAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        
        setTitle("Admin Panel - Pending Events");
        initializeViews();
        setupViewModel();
        setupRecyclerView();
    }
    
    private void initializeViews() {
        RecyclerView recyclerView = findViewById(R.id.recyclerPendingEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new AdminEventAdapter();
        recyclerView.setAdapter(adapter);
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        
        eventViewModel.getPendingEvents().observe(this, events -> {
            adapter.setEvents(events);
        });
    }
    
    private void setupRecyclerView() {
        adapter.setOnApproveClickListener(event -> {
            event.setApproved(true);
            eventViewModel.update(event);
        });
        
        adapter.setOnRejectClickListener(event -> {
            eventViewModel.delete(event);
        });
    }
}