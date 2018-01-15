package com.example.myapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Tasks.class, taskInfo.class, Check.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TasksDao tasksDao();
    public abstract TaskInfoDao taskInfoDao();
    public abstract CheckDao checkDao();
}