package com.example.localeventhub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.activities.EventDetailActivity;
import com.example.localeventhub.adapters.EventAdapter;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.viewmodels.EventViewModel;

public class EventListFragment extends Fragment {
    private EventViewModel eventViewModel;
    private EventAdapter adapter;
    private Spinner spinnerDistrict;
    
    private final String[] DISTRICTS = {"All", "Bole", "Arada", "Yeka", "Nifas Silk"};
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        
        initializeViews(view);
        setupViewModel();
        setupDistrictSpinner();
        setupRecyclerView();
        
        return view;
    }
    
    private void initializeViews(View view) {
        spinnerDistrict = view.findViewById(R.id.spinnerDistrictFilter);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);
    }
    
    private void setupViewModel() {
        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        
        eventViewModel.getSelectedDistrict().observe(getViewLifecycleOwner(), district -> {
            if ("All".equals(district)) {
                eventViewModel.getAllApprovedEvents().observe(getViewLifecycleOwner(), 
                    events -> adapter.setEvents(events));
            } else {
                eventViewModel.getEventsByDistrict(district).observe(getViewLifecycleOwner(), 
                    events -> adapter.setEvents(events));
            }
        });
    }
    
    private void setupDistrictSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), 
                android.R.layout.simple_spinner_item, DISTRICTS);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(spinnerAdapter);
        
        spinnerDistrict.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrict = DISTRICTS[position];
                eventViewModel.setSelectedDistrict(selectedDistrict);
            }
            
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }
    
    private void setupRecyclerView() {
        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(getActivity(), EventDetailActivity.class);
            intent.putExtra("EVENT_ID", event.getId());
            intent.putExtra("USER_ID", requireActivity().getIntent().getIntExtra("USER_ID", -1));
            intent.putExtra("USER_ROLE", requireActivity().getIntent().getStringExtra("USER_ROLE"));
            startActivity(intent);
        });
    }
}