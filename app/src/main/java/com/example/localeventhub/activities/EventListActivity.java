package com.example.localeventhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.adapters.EventAdapter;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private Spinner spinnerDistrictFilter;
    private ProgressBar progressBar;
    private TextView tvEmptyMessage;

    private final String[] DISTRICTS = {"All", "Bole", "Arada", "Yeka", "Nifas Silk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        setTitle("Available Events");
        initializeViews();
        setupViewModel();
        setupDistrictFilter();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewEvents);
        spinnerDistrictFilter = findViewById(R.id.spinnerDistrictFilter);
        progressBar = findViewById(R.id.progressBar);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(this, EventDetailActivity.class);
            intent.putExtra("EVENT_ID", event.getId());
            startActivity(intent);
        });
    }

    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventViewModel.getFilteredApprovedEvents().observe(this, events -> {
            progressBar.setVisibility(View.GONE);
            if (events != null && !events.isEmpty()) {
                adapter.setEvents(events);
                tvEmptyMessage.setVisibility(View.GONE);
            } else {
                adapter.setEvents(new ArrayList<>());
                tvEmptyMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupDistrictFilter() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, DISTRICTS);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrictFilter.setAdapter(spinnerAdapter);

        spinnerDistrictFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                eventViewModel.setDistrictFilter(DISTRICTS[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}