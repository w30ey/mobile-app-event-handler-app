package com.example.localeventhub.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.adapters.EventAdapter;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

import java.util.ArrayList;

public class AdminPanelActivity extends BaseActivity {
    private EventViewModel eventViewModel;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        setTitle(R.string.admin_panel_title);
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
                tvEmptyMessage.setText(R.string.no_pending_events);
            }
        });
    }

    private void showApprovalDialog(Event event) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.approve_dialog_title)
                .setMessage(getString(R.string.approve_dialog_message, event.getTitle()))
                .setPositiveButton(R.string.approve_button, (dialog, which) -> {
                    event.setApproved(true);
                    eventViewModel.update(event);
                })
                .setNegativeButton(R.string.reject_button, (dialog, which) -> {
                    eventViewModel.delete(event);
                })
                .setNeutralButton(R.string.cancel_button, null)
                .show();
    }
}