package com.example.localeventhub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.activities.CreateEditEventActivity;
import com.example.localeventhub.activities.EventDetailActivity;
import com.example.localeventhub.adapters.OrganizerEventAdapter;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

public class OrganizerEventFragment extends Fragment {
    private EventViewModel eventViewModel;
    private OrganizerEventAdapter adapter;
    private int organizerId;
    
    public static OrganizerEventFragment newInstance(int organizerId) {
        OrganizerEventFragment fragment = new OrganizerEventFragment();
        Bundle args = new Bundle();
        args.putInt("ORGANIZER_ID", organizerId);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organizer_events, container, false);
        
        if (getArguments() != null) {
            organizerId = getArguments().getInt("ORGANIZER_ID");
        }
        
        initializeViews(view);
        setupViewModel();
        setupRecyclerView();
        
        return view;
    }
    
    private void initializeViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerOrganizerEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new OrganizerEventAdapter();
        recyclerView.setAdapter(adapter);
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        
        eventViewModel.getEventsByOrganizer(organizerId).observe(getViewLifecycleOwner(), 
            events -> adapter.setEvents(events));
    }
    
    private void setupRecyclerView() {
        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(getActivity(), EventDetailActivity.class);
            intent.putExtra("EVENT_ID", event.getId());
            intent.putExtra("USER_ID", organizerId);
            intent.putExtra("USER_ROLE", "organizer");
            startActivity(intent);
        });
        
        adapter.setOnEditClickListener(event -> {
            Intent intent = new Intent(getActivity(), CreateEditEventActivity.class);
            intent.putExtra("USER_ID", organizerId);
            intent.putExtra("MODE", "edit");
            intent.putExtra("EVENT_ID", event.getId());
            startActivity(intent);
        });
        
        adapter.setOnDeleteClickListener(event -> {
            eventViewModel.delete(event);
        });
    }
}