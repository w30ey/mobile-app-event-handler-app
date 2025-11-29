package com.example.localeventhub.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.localeventhub.R;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

public class EventDetailActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        
        initializeViews();
        setupViewModel();
        displayEventDetails();
    }
    
    private void initializeViews() {
        Button btnFavorite = findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(v -> {
            Toast.makeText(this, "Favorite feature coming soon!", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
    
    private void displayEventDetails() {
        // For now, show static data
        TextView tvTitle = findViewById(R.id.tvEventTitle);
        TextView tvDescription = findViewById(R.id.tvEventDescription);
        TextView tvDate = findViewById(R.id.tvEventDate);
        TextView tvLocation = findViewById(R.id.tvEventLocation);
        TextView tvDistrict = findViewById(R.id.tvEventDistrict);
        
        tvTitle.setText("Sample Event");
        tvDescription.setText("This is a sample event description. More details coming soon!");
        tvDate.setText("Date: 2024-02-15");
        tvLocation.setText("Location: Mesquel Square");
        tvDistrict.setText("District: Arada");
    }
}