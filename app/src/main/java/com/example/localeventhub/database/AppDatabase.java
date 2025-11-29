package com.example.localeventhub.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.localeventhub.models.Event;
import com.example.localeventhub.models.User;

@Database(entities = {Event.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    
    public abstract EventDao eventDao();
    public abstract UserDao userDao();
    
    private static volatile AppDatabase INSTANCE;
    
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "event_database")
                            .fallbackToDestructiveMigration()
                            .build();
                    // Seed initial data
                    seedInitialData();
                }
            }
        }
        return INSTANCE;
    }
    
    private static void seedInitialData() {
        new Thread(() -> {
            // Seed users
            UserDao userDao = INSTANCE.userDao();
            userDao.insert(new User(1, "user1", "pass123", "user"));
            userDao.insert(new User(2, "organizer1", "pass123", "organizer"));
            userDao.insert(new User(3, "admin", "admin123", "admin"));
            
            // Seed events
            EventDao eventDao = INSTANCE.eventDao();
            eventDao.insert(new Event("Music Festival", "Annual music festival", "2024-02-15", 
                    "Mesquel Square", "Arada", 2, true));
            eventDao.insert(new Event("Art Exhibition", "Local artists exhibition", "2024-02-20", 
                    "National Museum", "Arada", 2, true));
            eventDao.insert(new Event("Tech Conference", "Technology innovation conference", "2024-03-01", 
                    "Sheraton Hotel", "Bole", 2, false)); // Pending approval
            eventDao.insert(new Event("Food Festival", "Traditional food tasting", "2024-02-25", 
                    "Bole Medhanialem", "Bole", 2, true));
            eventDao.insert(new Event("Book Fair", "Annual book fair", "2024-03-05", 
                    "Yeka Abado", "Yeka", 2, true));
        }).start();
    }
}