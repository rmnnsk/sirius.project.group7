package com.example.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface TaskInfoDao {

    @Insert
    public void insertAll(taskInfo... taskInfos);

    @Query("SELECT taskInfo.description FROM taskInfo WHERE task_id = :id")
    public String getDescription(int id);

    @Delete
    public void delete(taskInfo task_info);
}
