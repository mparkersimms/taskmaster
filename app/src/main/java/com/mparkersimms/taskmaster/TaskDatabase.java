package com.mparkersimms.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mparkersimms.taskmaster.daos.TaskDao;

@Database(entities = {Task.class}, version=1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
