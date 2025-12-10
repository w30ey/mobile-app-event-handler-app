package com.example.localeventhub.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import com.example.localeventhub.R;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

public class CreateEditEventActivity extends BaseActivity {
    private EditText etTitle, etDescription, etDate, etLocation, etPrice;
    private Spinner spinnerDistrict, spinnerCategory;
    private Button btnSave;
    private EventViewModel eventViewModel;
    private int userId;
    private String mode;
    
    private final String[] DISTRICTS = {"Bole", "Arada", "Yeka", "Nifas Silk"};
    private final String[] CATEGORIES = {"Music", "Art", "Tech", "Food", "Sport"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_event);
        
        initializeViews();
        setupViewModel();
        loadIntentData();
        setupSpinners();
        setupSaveButton();
    }
    
    private void initializeViews() {
        etTitle = findViewById(R.id.etEventTitle);
        etDescription = findViewById(R.id.etEventDescription);
        etDate = findViewById(R.id.etEventDate);
        etLocation = findViewById(R.id.etEventLocation);
        etPrice = findViewById(R.id.etEventPrice);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSaveEvent);

        etTitle.setHint(R.string.hint_event_title);
        etDescription.setHint(R.string.hint_event_description);
        etDate.setHint(R.string.hint_event_date);
        etLocation.setHint(R.string.hint_event_location);
        etPrice.setHint(R.string.hint_event_price);
        btnSave.setText(R.string.save_event_button);
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
    
    private void loadIntentData() {
        userId = getIntent().getIntExtra("USER_ID", -1);
        mode = getIntent().getStringExtra("MODE");
        
        if ("edit".equals(mode)) {
            setTitle(R.string.edit_event_title);
            // For now, we'll just show create functionality
            populateSampleData();
        } else {
            setTitle(R.string.create_event_title);
        }
    }
    
    private void populateSampleData() {
        // Sample data for editing
        etTitle.setText("Sample Event");
        etDescription.setText("This is a sample event description");
        etDate.setText("2024-03-15");
        etLocation.setText("Sample Location");
        etPrice.setText("500");
        spinnerDistrict.setSelection(0); // Select first district
        spinnerCategory.setSelection(0); // Select first category
    }
    
    private void setupSpinners() {
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_spinner_item, DISTRICTS);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(districtAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_spinner_item, CATEGORIES);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
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
        String category = spinnerCategory.getSelectedItem().toString();
        String priceStr = etPrice.getText().toString().trim();
        
        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_fill_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }
        
        double price = Double.parseDouble(priceStr);

        // Create new event - organizers need admin approval
        boolean needsApproval = "organizer".equals(getIntent().getStringExtra("USER_ROLE"));
        
        Event newEvent = new Event(title, description, date, location, district, price, category, userId, !needsApproval);
        eventViewModel.insert(newEvent);
        
        String message = needsApproval ? 
            getString(R.string.event_submitted_for_approval, title) :
            getString(R.string.event_created_successfully, title);
            
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Add console log for debugging
        System.out.println("EVENT CREATED: " + title + " | Approved: " + !needsApproval);

        finish();
    }
}