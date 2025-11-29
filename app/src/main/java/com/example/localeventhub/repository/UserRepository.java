package com.example.localeventhub.repository;

import android.app.Application;

import com.example.localeventhub.database.AppDatabase;
import com.example.localeventhub.database.UserDao;
import com.example.localeventhub.models.User;

public class UserRepository {
    private UserDao userDao;
    
    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        userDao = database.userDao();
    }
    
    public User login(String username, String password) {
        return userDao.login(username, password);
    }
    
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }
}