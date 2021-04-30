package com.example.taskmaster.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskmaster.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    public void insert (Task task);

    @Query("SELECT * FROM Task")
    List<Task> findAll();

    @Query("SELECT * FROM Task WHERE id = :id")
    List<Task> findById(int id);
}
