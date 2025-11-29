package com.example.localeventhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.adapters.EventAdapter;
import com.example.localeventhub.viewmodels.EventViewModel;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        setTitle("My Favorite Events");
        initializeViews();
        setupViewModel();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewFavorites);
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

        eventViewModel.getFavoriteEvents().observe(this, events -> {
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
}