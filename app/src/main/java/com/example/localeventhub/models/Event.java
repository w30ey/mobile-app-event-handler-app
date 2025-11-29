package com.example.localeventhub.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "events")
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String date;
    private String location;
    private String district;
    @ColumnInfo(name = "organizer_id")
    private int organizerId;
    private boolean approved;
    private boolean favorite;

    public Event(String title, String description, String date, String location, 
                 String district, int organizerId, boolean approved) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.district = district;
        this.organizerId = organizerId;
        this.approved = approved;
        this.favorite = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public int getOrganizerId() { return organizerId; }
    public void setOrganizerId(int organizerId) { this.organizerId = organizerId; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}