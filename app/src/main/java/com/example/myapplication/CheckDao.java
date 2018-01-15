package com.example.myapplication;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CheckDao {

    @Query("SELECT * FROM checks")
    List<Check> loadAllChecks();

    @Query("SELECT * FROM checks WHERE task_id = :id")
    List<Check> loadChecksById(int id);

    @Delete
    void delete(Check check);

    @Query("DELETE FROM checks WHERE task_id = :id")
    void deleteAllById(int id);

    @Insert
    void insertAll(Check... checks);
}
