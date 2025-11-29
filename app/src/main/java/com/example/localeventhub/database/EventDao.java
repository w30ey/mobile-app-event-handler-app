package com.example.localeventhub.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.localeventhub.models.Event;
import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insert(Event event);
    @Update
    void update(Event event);
    @Delete
    void delete(Event event);
    
    @Query("SELECT * FROM events WHERE approved = 1 ORDER BY date ASC")
    LiveData<List<Event>> getAllApprovedEvents();
    @Query("SELECT * FROM events WHERE approved = 0 ORDER BY date ASC")
    LiveData<List<Event>> getPendingEvents();
    @Query("SELECT * FROM events WHERE organizer_id = :organizerId ORDER BY date ASC")
    LiveData<List<Event>> getEventsByOrganizer(int organizerId);
    @Query("SELECT * FROM events WHERE id = :eventId")
    LiveData<Event> getEventById(int eventId);
    @Query("SELECT * FROM events WHERE approved = 1 AND district = :district ORDER BY date ASC")
    LiveData<List<Event>> getEventsByDistrict(String district);
    @Query("UPDATE events SET favorite = :isFavorite WHERE id = :eventId")
    void updateFavoriteStatus(int eventId, boolean isFavorite);
}