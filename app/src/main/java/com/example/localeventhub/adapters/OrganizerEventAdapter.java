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

public class OrganizerEventAdapter extends RecyclerView.Adapter<OrganizerEventAdapter.EventViewHolder> {
    private List<Event> events = new ArrayList<>();
    private OnItemClickListener itemClickListener;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;
    
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_organizer_event, parent, false);
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
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }
    
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }
    
    class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDate, tvDistrict, tvStatus;
        private Button btnEdit, btnDelete;
        
        EventViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvEventTitle);
            tvDate = itemView.findViewById(R.id.tvEventDate);
            tvDistrict = itemView.findViewById(R.id.tvEventDistrict);
            tvStatus = itemView.findViewById(R.id.tvEventStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
        
        void bind(Event event) {
            tvTitle.setText(event.getTitle());
            tvDate.setText(event.getDate());
            tvDistrict.setText(event.getDistrict());
            tvStatus.setText(event.isApproved() ? "Approved" : "Pending Approval");
            tvStatus.setTextColor(itemView.getContext().getColor(
                event.isApproved() ? R.color.approved : R.color.pending));
            
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(event);
                }
            });
            
            btnEdit.setOnClickListener(v -> {
                if (editClickListener != null) {
                    editClickListener.onEditClick(event);
                }
            });
            
            btnDelete.setOnClickListener(v -> {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(event);
                }
            });
        }
    }
    
    public interface OnItemClickListener {
        void onItemClick(Event event);
    }
    
    public interface OnEditClickListener {
        void onEditClick(Event event);
    }
    
    public interface OnDeleteClickListener {
        void onDeleteClick(Event event);
    }
}