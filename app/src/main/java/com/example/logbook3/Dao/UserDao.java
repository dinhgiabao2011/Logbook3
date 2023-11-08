package com.example.logbook3.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.logbook3.Models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    Long addUser(User user);
    @Query("SELECT * FROM users ORDER BY name")
    List<User> getAllUsers();
    @Delete
    void deleteUser(User user);
}