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
        
        if ("edit".equals(mode)) {
            setTitle("Edit Event");
            // For now, we'll just show create functionality
            populateSampleData();
        } else {
            setTitle("Create Event");
        }
    }
    
    private void populateSampleData() {
        // Sample data for editing
        etTitle.setText("Sample Event");
        etDescription.setText("This is a sample event description");
        etDate.setText("2024-03-15");
        etLocation.setText("Sample Location");
        spinnerDistrict.setSelection(0); // Select first district
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
        
        // Create new event - organizers need admin approval
        boolean needsApproval = "organizer".equals(getIntent().getStringExtra("USER_ROLE"));
        
        Event newEvent = new Event(title, description, date, location, district, userId, !needsApproval);
        eventViewModel.insert(newEvent);
        
        String message = needsApproval ? 
            "Event '" + title + "' created and submitted for admin approval!" : 
            "Event '" + title + "' created successfully!";
            
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Add console log for debugging
        System.out.println("EVENT CREATED: " + title + " | Approved: " + !needsApproval);

        finish();
    }
}