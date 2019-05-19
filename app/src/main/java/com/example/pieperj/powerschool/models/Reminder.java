package com.example.pieperj.powerschool.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int reminderId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name="description")
    private String description;

    @ColumnInfo(name="time")
    private long time;

    @ColumnInfo(name="complete")
    private boolean complete;

    public Reminder(String title, String description, long time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }


    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "reminderId=" + reminderId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", complete=" + complete +
                '}';
    }


}
