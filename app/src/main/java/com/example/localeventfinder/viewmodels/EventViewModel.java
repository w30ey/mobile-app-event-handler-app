package com.example.localeventfinder.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.localeventfinder.data.SimpleDataManager;
import com.example.localeventfinder.models.Event;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private SimpleDataManager dataManager;
    private MutableLiveData<List<Event>> allEvents = new MutableLiveData<>();
    
    public EventViewModel(Application application) {
        super(application);
        dataManager = SimpleDataManager.getInstance();
        loadAllEvents();
    }
    
    public MutableLiveData<List<Event>> getAllEvents() {
        return allEvents;
    }
    
    public void loadAllEvents() {
        allEvents.setValue(dataManager.getAllEvents());
    }
    
    public void insertEvent(Event event) {
        dataManager.addEvent(event);
        loadAllEvents();
    }
    
    public void updateEvent(Event event) {
        dataManager.updateEvent(event);
        loadAllEvents();
    }
    
    public void deleteEvent(Event event) {
        dataManager.deleteEvent(event);
        loadAllEvents();
    }
    
    public void toggleEventFavorite(Event event) {
        event.setFavorite(!event.isFavorite());
        dataManager.updateEvent(event);
        loadAllEvents();
    }
    
    public void searchEvents(String query) {
        if (query.isEmpty()) {
            loadAllEvents();
        } else {
            allEvents.setValue(dataManager.searchEvents(query));
        }
    }
    
    public void filterEventsByCategory(String category) {
        if (category.equals("All")) {
            loadAllEvents();
        } else {
            allEvents.setValue(dataManager.getEventsByCategory(category));
        }
    }
    
    public List<Event> getFavoriteEvents() {
        return dataManager.getFavoriteEvents();
    }
    
    public void insertSampleData() {
        // Data is already initialized
    }
}