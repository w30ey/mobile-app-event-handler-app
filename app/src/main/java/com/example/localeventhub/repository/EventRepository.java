package com.example.localeventhub.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.localeventhub.database.AppDatabase;
import com.example.localeventhub.database.EventDao;
import com.example.localeventhub.models.Event;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    private EventDao eventDao;
    private ExecutorService executorService;
    
    public EventRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        eventDao = database.eventDao();
        executorService = Executors.newSingleThreadExecutor();
    }
    
    public LiveData<List<Event>> getAllApprovedEvents() {
        return eventDao.getAllApprovedEvents();
    }
    
    public LiveData<List<Event>> getPendingEvents() {
        return eventDao.getPendingEvents();
    }
    
    public LiveData<List<Event>> getEventsByOrganizer(int organizerId) {
        return eventDao.getEventsByOrganizer(organizerId);
    }
    
    public LiveData<List<Event>> getEventsByDistrict(String district) {
        return eventDao.getEventsByDistrict(district);
    }
    
    public LiveData<Event> getEventById(int eventId) {
        return eventDao.getEventById(eventId);
    }
    
    public void insert(Event event) {
        executorService.execute(() -> eventDao.insert(event));
    }
    
    public void update(Event event) {
        executorService.execute(() -> eventDao.update(event));
    }
    
    public void delete(Event event) {
        executorService.execute(() -> eventDao.delete(event));
    }
    
    public void toggleFavorite(int eventId, boolean isFavorite) {
        executorService.execute(() -> eventDao.updateFavoriteStatus(eventId, isFavorite));
    }
}