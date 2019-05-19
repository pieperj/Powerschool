package com.example.pieperj.powerschool.models;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminder")
    List<Reminder> getAllReminders();

    @Query("SELECT * FROM reminder WHERE complete = :complete")
    List<Reminder> getReminders(boolean complete);

    @Query("SELECT * FROM reminder WHERE time > :time")
    List<Reminder> getRemindersBeyondTime(long time);

    @Update
    void update(Reminder reminder);

    @Insert
    long insert(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

}
