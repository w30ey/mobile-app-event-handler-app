package com.example.localeventhub.repository;

import android.app.Application;
import com.example.localeventhub.database.AppDatabase;
import com.example.localeventhub.database.UserDao;
import com.example.localeventhub.models.User;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRepository {
    private UserDao userDao;
    private ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        userDao = database.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public User login(String username, String password) {
        Callable<User> callable = () -> userDao.login(username, password);
        Future<User> future = executorService.submit(callable);
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(int userId) {
        Callable<User> callable = () -> userDao.getUserById(userId);
        Future<User> future = executorService.submit(callable);
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}