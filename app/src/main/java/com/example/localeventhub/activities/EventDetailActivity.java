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
    private int eventId;
    private Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        setTitle("Event Details");
        setupViewModel();
        loadEventDetails();
    }

    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }

    private void loadEventDetails() {
        eventId = getIntent().getIntExtra("EVENT_ID", -1);
        if (eventId == -1) {
            Toast.makeText(this, "Error: Event not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        eventViewModel.getEventById(eventId).observe(this, event -> {
            if (event != null) {
                currentEvent = event;
                displayEventDetails(event);
                setupFavoriteButton(event);
            }
        });
    }

    private void displayEventDetails(Event event) {
        TextView tvTitle = findViewById(R.id.tvEventTitle);
        TextView tvDescription = findViewById(R.id.tvEventDescription);
        TextView tvDate = findViewById(R.id.tvEventDate);
        TextView tvLocation = findViewById(R.id.tvEventLocation);
        TextView tvDistrict = findViewById(R.id.tvEventDistrict);

        tvTitle.setText(event.getTitle());
        tvDescription.setText(event.getDescription());
        tvDate.setText("📅 Date: " + event.getDate());
        tvLocation.setText("📍 Location: " + event.getLocation());
        tvDistrict.setText("🏘️ District: " + event.getDistrict());
    }

    private void setupFavoriteButton(Event event) {
        Button btnFavorite = findViewById(R.id.btnFavorite);
        updateFavoriteButtonUI(event.isFavorite());

        btnFavorite.setOnClickListener(v -> {
            boolean newFavoriteState = !currentEvent.isFavorite();
            eventViewModel.toggleFavorite(currentEvent.getId(), newFavoriteState);
        });
    }

    private void updateFavoriteButtonUI(boolean isFavorite) {
        Button btnFavorite = findViewById(R.id.btnFavorite);
        if (isFavorite) {
            btnFavorite.setText("❤️ Remove Favorite");
            btnFavorite.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            btnFavorite.setText("🤍 Add Favorite");
            btnFavorite.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }
}