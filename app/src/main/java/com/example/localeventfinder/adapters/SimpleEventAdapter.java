package com.example.localeventfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventfinder.R;
import com.example.localeventfinder.models.Event;

import java.util.List;

public class SimpleEventAdapter extends RecyclerView.Adapter<SimpleEventAdapter.ViewHolder> {
    private List<Event> events;
    
    public SimpleEventAdapter(List<Event> events) {
        this.events = events;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_simple, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.title.setText(event.getTitle());
        holder.date.setText(event.getDate() + " at " + event.getTime());
        holder.venue.setText(event.getVenue());
        holder.category.setText(event.getCategory());
    }
    
    @Override
    public int getItemCount() {
        return events.size();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, venue, category;
        
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.text_title);
            date = view.findViewById(R.id.text_date);
            venue = view.findViewById(R.id.text_venue);
            category = view.findViewById(R.id.text_category);
        }
    }
}