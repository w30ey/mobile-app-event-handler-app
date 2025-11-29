package com.example.localeventhub.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.repository.EventRepository;
import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository repository;
    private LiveData<List<Event>> allApprovedEvents;
    private LiveData<List<Event>> pendingEvents;
    private MutableLiveData<String> selectedDistrict = new MutableLiveData<>("All");
    
    public EventViewModel(Application application) {
        super(application);
        repository = new EventRepository(application);
        allApprovedEvents = repository.getAllApprovedEvents();
        pendingEvents = repository.getPendingEvents();
    }
    
    public LiveData<List<Event>> getAllApprovedEvents() { return allApprovedEvents; }
    public LiveData<List<Event>> getPendingEvents() { return pendingEvents; }
    public LiveData<List<Event>> getEventsByOrganizer(int organizerId) { 
        return repository.getEventsByOrganizer(organizerId); 
    }
    public LiveData<List<Event>> getEventsByDistrict(String district) { 
        return repository.getEventsByDistrict(district); 
    }
    public LiveData<Event> getEventById(int eventId) { 
        return repository.getEventById(eventId); 
    }
    public LiveData<List<Event>> getFavoriteEvents() {
        return repository.getFavoriteEvents();
    }
    public void insert(Event event) { repository.insert(event); }
    public void update(Event event) { repository.update(event); }
    public void delete(Event event) { repository.delete(event); }
    public void toggleFavorite(int eventId, boolean isFavorite) { 
        repository.toggleFavorite(eventId, isFavorite); 
    }
    public MutableLiveData<String> getSelectedDistrict() { return selectedDistrict; }
    public void setSelectedDistrict(String district) { selectedDistrict.setValue(district); }
}