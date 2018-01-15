package com.example.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TasksDao {

    //Получить все таски из бд
    @Query("SELECT * FROM tasks")
    List<Tasks> loadAll();

    //Получить таск с определённым id
    @Query("SELECT * FROM tasks WHERE id = :taskID")
    Tasks loadAllbyTaskId(int taskID);

    //Удаление таска из бд
    @Delete
    void delete(Tasks task);

    @Query("DELETE FROM tasks")
    public void deleteAll();

    //Добавить таски в бд
    @Insert
    void insertAll(Tasks... tasks);
}

