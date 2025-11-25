package com.example.localeventfinder.data;

import com.example.localeventfinder.models.Event;
import com.example.localeventfinder.models.User;

import java.util.ArrayList;
import java.util.List;

public class SimpleDataManager {
    private static SimpleDataManager instance;
    private List<Event> events;
    private List<User> users;
    private User currentUser;
    
    private SimpleDataManager() {
        events = new ArrayList<>();
        users = new ArrayList<>();
        initializeSampleData();
    }
    
    public static SimpleDataManager getInstance() {
        if (instance == null) {
            instance = new SimpleDataManager();
        }
        return instance;
    }
    
    private void initializeSampleData() {
        // Initialize users
        users.add(new User("admin", "admin123", "admin"));
        users.add(new User("user", "user123", "user"));
        
        // Initialize events
        events.add(new Event("Java Developers Meetup", 
                "Monthly meetup for Java developers to share knowledge and network", 
                "2024-12-15", "18:00", "Tech Hub Addis", "Technology"));
        events.add(new Event("Local Music Festival", 
                "Annual music festival featuring local artists", 
                "2024-12-20", "16:00", "City Park", "Music"));
        events.add(new Event("Art Exhibition", 
                "Contemporary art from local artists", 
                "2024-12-10", "10:00", "National Museum", "Art"));
        events.add(new Event("Basketball Tournament", 
                "Community basketball championship", 
                "2024-12-22", "14:00", "Sports Complex", "Sports"));
        events.add(new Event("Food & Drink Fair", 
                "Taste local cuisine and beverages", 
                "2024-12-18", "12:00", "Downtown Square", "Food & Drink"));
    }
    
    // Event methods
    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }
    
    public void addEvent(Event event) {
        events.add(event);
    }
    
    public void updateEvent(Event event) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == event.getId()) {
                events.set(i, event);
                break;
            }
        }
    }
    
    public void deleteEvent(Event event) {
        events.removeIf(e -> e.getId() == event.getId());
    }
    
    public List<Event> searchEvents(String query) {
        List<Event> results = new ArrayList<>();
        for (Event event : events) {
            if (event.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                event.getDescription().toLowerCase().contains(query.toLowerCase())) {
                results.add(event);
            }
        }
        return results;
    }
    
    public List<Event> getEventsByCategory(String category) {
        List<Event> results = new ArrayList<>();
        for (Event event : events) {
            if (event.getCategory().equals(category)) {
                results.add(event);
            }
        }
        return results;
    }
    
    public List<Event> getFavoriteEvents() {
        List<Event> results = new ArrayList<>();
        for (Event event : events) {
            if (event.isFavorite()) {
                results.add(event);
            }
        }
        return results;
    }
    
    // User methods
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return user;
            }
        }
        return null;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
}