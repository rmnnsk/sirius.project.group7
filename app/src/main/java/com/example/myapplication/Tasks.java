package com.example.myapplication;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Tasks {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "short_description")
    private String shortDescriprion;

    @ColumnInfo(name = "expire_time")
    private long expireTime;

    @ColumnInfo(name = "task_id")
    private String taskId;

    public Tasks(String shortDescriprion, long expireTime, String taskId) {
        this.shortDescriprion = shortDescriprion ;
        this.expireTime = expireTime;
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescriprion() {
        return shortDescriprion;
    }

    public void setShortDescriprion(String shortDescriprion) {
        this.shortDescriprion = shortDescriprion;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}

