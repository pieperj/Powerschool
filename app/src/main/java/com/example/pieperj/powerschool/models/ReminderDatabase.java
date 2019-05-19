package com.example.pieperj.powerschool.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;

public class ReminderDatabase {
    private static final ReminderDatabase ourInstance = new ReminderDatabase();

    private AppDatabase database;

    private List<Reminder> reminders;

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public static ReminderDatabase getInstance() {
        return ourInstance;
    }

    private ReminderDatabase() {
        reminders = new ArrayList<>();
    }

    public AppDatabase getDatabase(Context context){
        if(database == null){
            database = Room.databaseBuilder(context, AppDatabase.class,
                    "reminder").build();
        }

        return database;
    }

    public void addReminder(Reminder reminder) {
        this.reminders.add(reminder);
    }

    public void addReminders(List<Reminder> reminders){
        this.reminders.addAll(reminders);
    }

    public List<Reminder> getAllReminders(){
        return reminders;
    }

    public void clearReminders(){
        reminders.clear();
    }



}
