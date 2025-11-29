package com.example.localeventhub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> events = new ArrayList<>();
    private OnItemClickListener listener;
    
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event);
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(event);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return events.size();
    }
    
    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    
    static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDate, tvDistrict;
        
        EventViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvEventTitle);
            tvDate = itemView.findViewById(R.id.tvEventDate);
            tvDistrict = itemView.findViewById(R.id.tvEventDistrict);
        }
        
        void bind(Event event) {
            tvTitle.setText(event.getTitle());
            tvDate.setText(event.getDate());
            tvDistrict.setText(event.getDistrict());
        }
    }
    
    public interface OnItemClickListener {
        void onItemClick(Event event);
    }
}