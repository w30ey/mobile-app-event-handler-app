package com.example.localeventhub.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.localeventhub.models.Event;
import com.example.localeventhub.repository.EventRepository;
import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository repository;
    private LiveData<List<Event>> pendingEvents;
    private MutableLiveData<String> selectedDistrict = new MutableLiveData<>("All");
    private LiveData<List<Event>> filteredApprovedEvents;

    public EventViewModel(Application application) {
        super(application);
        repository = new EventRepository(application);
        pendingEvents = repository.getPendingEvents();

        // Create a transformation that reacts to changes in the selected district
        filteredApprovedEvents = Transformations.switchMap(selectedDistrict, district -> {
            if ("All".equals(district)) {
                return repository.getAllApprovedEvents();
            } else {
                return repository.getEventsByDistrict(district);
            }
        });
    }

    // The UI will observe this single LiveData for the list of events
    public LiveData<List<Event>> getFilteredApprovedEvents() {
        return filteredApprovedEvents;
    }

    // Call this method to change the filter
    public void setDistrictFilter(String district) {
        selectedDistrict.setValue(district);
    }

    public LiveData<List<Event>> getPendingEvents() { return pendingEvents; }
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
}