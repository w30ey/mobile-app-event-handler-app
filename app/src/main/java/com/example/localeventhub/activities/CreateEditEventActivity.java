package com.example.localeventhub.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.localeventhub.R;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

public class CreateEditEventActivity extends AppCompatActivity {
    private EditText etTitle, etDescription, etDate, etLocation;
    private Spinner spinnerDistrict;
    private Button btnSave;
    private EventViewModel eventViewModel;
    private int userId;
    private String mode;
    private Event existingEvent;
    
    private final String[] DISTRICTS = {"Bole", "Arada", "Yeka", "Nifas Silk"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_event);
        
        initializeViews();
        setupViewModel();
        loadIntentData();
        setupDistrictSpinner();
        setupSaveButton();
    }
    
    private void initializeViews() {
        etTitle = findViewById(R.id.etEventTitle);
        etDescription = findViewById(R.id.etEventDescription);
        etDate = findViewById(R.id.etEventDate);
        etLocation = findViewById(R.id.etEventLocation);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        btnSave = findViewById(R.id.btnSaveEvent);
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
    
    private void loadIntentData() {
        userId = getIntent().getIntExtra("USER_ID", -1);
        mode = getIntent().getStringExtra("MODE");
        int eventId = getIntent().getIntExtra("EVENT_ID", -1);
        
        if ("edit".equals(mode) && eventId != -1) {
            loadEventForEditing(eventId);
            setTitle("Edit Event");
        } else {
            setTitle("Create Event");
        }
    }
    
    private void loadEventForEditing(int eventId) {
        eventViewModel.getEventById(eventId).observe(this, event -> {
            if (event != null) {
                existingEvent = event;
                populateForm(event);
            }
        });
    }
    
    private void populateForm(Event event) {
        etTitle.setText(event.getTitle());
        etDescription.setText(event.getDescription());
        etDate.setText(event.getDate());
        etLocation.setText(event.getLocation());
        
        // Set district spinner
        for (int i = 0; i < DISTRICTS.length; i++) {
            if (DISTRICTS[i].equals(event.getDistrict())) {
                spinnerDistrict.setSelection(i);
                break;
            }
        }
    }
    
    private void setupDistrictSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_spinner_item, DISTRICTS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter);
    }
    
    private void setupSaveButton() {
        btnSave.setOnClickListener(v -> saveEvent());
    }
    
    private void saveEvent() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String district = spinnerDistrict.getSelectedItem().toString();
        
        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if ("edit".equals(mode) && existingEvent != null) {
            // Update existing event
            existingEvent.setTitle(title);
            existingEvent.setDescription(description);
            existingEvent.setDate(date);
            existingEvent.setLocation(location);
            existingEvent.setDistrict(district);
            eventViewModel.update(existingEvent);
            Toast.makeText(this, "Event updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Create new event
            Event newEvent = new Event(title, description, date, location, district, userId, false);
            eventViewModel.insert(newEvent);
            Toast.makeText(this, "Event created and submitted for approval", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
}