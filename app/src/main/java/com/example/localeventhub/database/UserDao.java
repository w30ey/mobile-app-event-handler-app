package com.example.localeventhub.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.localeventhub.models.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User login(String username, String password);
    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);
}