package com.example.localeventhub.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.adapters.EventAdapter;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

import java.util.ArrayList;

public class AdminPanelActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        setTitle("Admin Panel - Pending Events");
        initializeViews();
        setupViewModel();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewPendingEvents);
        progressBar = findViewById(R.id.progressBar);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this::showApprovalDialog);
    }

    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventViewModel.getPendingEvents().observe(this, events -> {
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

    private void showApprovalDialog(Event event) {
        new AlertDialog.Builder(this)
                .setTitle("Approve Event")
                .setMessage("Do you want to approve the event: " + event.getTitle() + "?")
                .setPositiveButton("Approve", (dialog, which) -> {
                    event.setApproved(true);
                    eventViewModel.update(event);
                })
                .setNegativeButton("Reject", (dialog, which) -> {
                    eventViewModel.delete(event);
                })
                .setNeutralButton("Cancel", null)
                .show();
    }
}