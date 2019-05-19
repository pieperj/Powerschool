package com.example.pieperj.powerschool.models;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Reminder.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ReminderDao reminderDao();


}
