package com.example.localeventhub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localeventhub.R;
import com.example.localeventhub.models.Event;

import java.util.ArrayList;
import java.util.List;

public class AdminEventAdapter extends RecyclerView.Adapter<AdminEventAdapter.EventViewHolder> {
    private List<Event> events = new ArrayList<>();
    private OnApproveClickListener approveClickListener;
    private OnRejectClickListener rejectClickListener;
    
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_event, parent, false);
        return new EventViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event);
    }
    
    @Override
    public int getItemCount() {
        return events.size();
    }
    
    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }
    
    public void setOnApproveClickListener(OnApproveClickListener listener) {
        this.approveClickListener = listener;
    }
    
    public void setOnRejectClickListener(OnRejectClickListener listener) {
        this.rejectClickListener = listener;
    }
    
    class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDate, tvDistrict, tvOrganizer;
        private Button btnApprove, btnReject;
        
        EventViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvEventTitle);
            tvDate = itemView.findViewById(R.id.tvEventDate);
            tvDistrict = itemView.findViewById(R.id.tvEventDistrict);
            tvOrganizer = itemView.findViewById(R.id.tvOrganizer);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
        
        void bind(Event event) {
            tvTitle.setText(event.getTitle());
            tvDate.setText(event.getDate());
            tvDistrict.setText(event.getDistrict());
            tvOrganizer.setText("Organizer ID: " + event.getOrganizerId());
            
            btnApprove.setOnClickListener(v -> {
                if (approveClickListener != null) {
                    approveClickListener.onApproveClick(event);
                }
            });
            
            btnReject.setOnClickListener(v -> {
                if (rejectClickListener != null) {
                    rejectClickListener.onRejectClick(event);
                }
            });
        }
    }
    
    public interface OnApproveClickListener {
        void onApproveClick(Event event);
    }
    
    public interface OnRejectClickListener {
        void onRejectClick(Event event);
    }
}