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
    private Event currentEvent;
    private int userId;
    private String userRole;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        
        initializeViews();
        setupViewModel();
        loadEventData();
    }
    
    private void initializeViews() {
        userId = getIntent().getIntExtra("USER_ID", -1);
        userRole = getIntent().getStringExtra("USER_ROLE");
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
    
    private void loadEventData() {
        int eventId = getIntent().getIntExtra("EVENT_ID", -1);
        
        eventViewModel.getEventById(eventId).observe(this, event -> {
            if (event != null) {
                currentEvent = event;
                displayEventDetails(event);
            }
        });
    }
    
    private void displayEventDetails(Event event) {
        TextView tvTitle = findViewById(R.id.tvEventTitle);
        TextView tvDescription = findViewById(R.id.tvEventDescription);
        TextView tvDate = findViewById(R.id.tvEventDate);
        TextView tvLocation = findViewById(R.id.tvEventLocation);
        TextView tvDistrict = findViewById(R.id.tvEventDistrict);
        Button btnFavorite = findViewById(R.id.btnFavorite);
        
        tvTitle.setText(event.getTitle());
        tvDescription.setText(event.getDescription());
        tvDate.setText("Date: " + event.getDate());
        tvLocation.setText("Location: " + event.getLocation());
        tvDistrict.setText("District: " + event.getDistrict());
        
        // Update favorite button
        updateFavoriteButton(btnFavorite, event.isFavorite());
        
        btnFavorite.setOnClickListener(v -> {
            boolean newFavoriteState = !event.isFavorite();
            eventViewModel.toggleFavorite(event.getId(), newFavoriteState);
            updateFavoriteButton(btnFavorite, newFavoriteState);
            Toast.makeText(this, newFavoriteState ? "Added to favorites" : "Removed from favorites", 
                          Toast.LENGTH_SHORT).show();
        });
    }
    
    private void updateFavoriteButton(Button button, boolean isFavorite) {
        button.setText(isFavorite ? "❤️ Remove Favorite" : "🤍 Add Favorite");
        button.setBackgroundColor(getColor(isFavorite ? R.color.favorite : R.color.unfavorite));
    }
}