package com.example.localeventhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.localeventhub.R;
import com.example.localeventhub.fragments.EventListFragment;
import com.example.localeventhub.fragments.OrganizerEventFragment;
import com.example.localeventhub.viewmodels.AuthViewModel;
import com.example.localeventhub.viewmodels.EventViewModel;

public class MainActivity extends AppCompatActivity {
    private int userId;
    private String userRole;
    private String username;
    
    private AuthViewModel authViewModel;
    private EventViewModel eventViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getUserDataFromIntent();
        initializeViewModels();
        setupUI();
        loadFragmentBasedOnRole();
    }
    
    private void getUserDataFromIntent() {
        Intent intent = getIntent();
        userId = intent.getIntExtra("USER_ID", -1);
        userRole = intent.getStringExtra("USER_ROLE");
        username = intent.getStringExtra("USERNAME");
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Welcome, " + username);
        }
    }
    
    private void initializeViewModels() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
    
    private void setupUI() {
        Button btnAdminPanel = findViewById(R.id.btnAdminPanel);
        Button btnCreateEvent = findViewById(R.id.btnCreateEvent);
        
        // Show admin button only for admin users
        if ("admin".equals(userRole)) {
            btnAdminPanel.setVisibility(android.view.View.VISIBLE);
            btnAdminPanel.setOnClickListener(v -> {
                Intent intent = new Intent(this, AdminPanelActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
        } else {
            btnAdminPanel.setVisibility(android.view.View.GONE);
        }
        
        // Show create event button for organizers
        if ("organizer".equals(userRole)) {
            btnCreateEvent.setVisibility(android.view.View.VISIBLE);
            btnCreateEvent.setOnClickListener(v -> {
                Intent intent = new Intent(this, CreateEditEventActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("MODE", "create");
                startActivity(intent);
            });
        } else {
            btnCreateEvent.setVisibility(android.view.View.GONE);
        }
    }
    
    private void loadFragmentBasedOnRole() {
        Fragment fragment;
        
        if ("organizer".equals(userRole)) {
            fragment = OrganizerEventFragment.newInstance(userId);
        } else {
            fragment = new EventListFragment();
        }
        
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            authViewModel.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}