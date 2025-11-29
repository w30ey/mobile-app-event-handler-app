package com.example.localeventhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.example.localeventhub.R;
import com.example.localeventhub.viewmodels.EventViewModel;

public class MainActivity extends AppCompatActivity {
    private int userId;
    private String userRole;
    private String username;
    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getUserDataFromIntent();
        setupUI();

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
    
    private void getUserDataFromIntent() {
        Intent intent = getIntent();
        userId = intent.getIntExtra("USER_ID", -1);
        userRole = intent.getStringExtra("USER_ROLE");
        username = intent.getStringExtra("USERNAME");
        
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome, " + username + "!");
        
        // Set activity title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Local Event Hub");
        }
    }
    
    private void setupUI() {
        CardView cardViewEvents = findViewById(R.id.cardViewEvents);
        CardView cardFavorites = findViewById(R.id.cardFavorites);
        CardView cardCreateEvent = findViewById(R.id.cardCreateEvent);
        CardView cardAdminPanel = findViewById(R.id.cardAdminPanel);

        // Role-specific visibility
        if ("admin".equals(userRole)) {
            cardAdminPanel.setVisibility(View.VISIBLE);
        } else if ("organizer".equals(userRole)) {
            cardCreateEvent.setVisibility(View.VISIBLE);
        }

        // Click Listeners
        cardViewEvents.setOnClickListener(v -> {
            Intent intent = new Intent(this, EventListActivity.class);
            startActivity(intent);
        });

        cardFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        });

        cardCreateEvent.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateEditEventActivity.class);
            intent.putExtra("USER_ID", userId);
            intent.putExtra("USER_ROLE", userRole);
            intent.putExtra("MODE", "create");
            startActivity(intent);
        });

        cardAdminPanel.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminPanelActivity.class);
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