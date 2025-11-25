package com.example.localeventfinder.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localeventfinder.R;
import com.example.localeventfinder.models.Event;
import com.example.localeventfinder.viewmodels.EventViewModel;

public class AddEventActivity extends AppCompatActivity {
    
    private EditText editTextTitle, editTextDescription, editTextDate, editTextTime, editTextVenue;
    private Spinner spinnerCategory;
    private Button buttonSave, buttonCancel;
    private EventViewModel eventViewModel;
    private String username;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        
        username = getIntent().getStringExtra("username");
        initializeViews();
        setupCategorySpinner();
        setupButtons();
    }
    
    private void initializeViews() {
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextTime = findViewById(R.id.edit_text_time);
        editTextVenue = findViewById(R.id.edit_text_venue);
        spinnerCategory = findViewById(R.id.spinner_category);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);
        
        eventViewModel = new androidx.lifecycle.ViewModelProvider(this).get(EventViewModel.class);
    }
    
    private void setupCategorySpinner() {
        String[] categories = {"Music", "Technology", "Art", "Sports", "Food & Drink"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }
    
    private void setupButtons() {
        buttonSave.setOnClickListener(v -> saveEvent());
        buttonCancel.setOnClickListener(v -> finish());
    }
    
    private void saveEvent() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String venue = editTextVenue.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        
        // Validation
        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty() || venue.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Create new event
        Event newEvent = new Event(title, description, date, time, venue, category);
        eventViewModel.insertEvent(newEvent);
        
        Toast.makeText(this, "Event added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}