package com.example.pieperj.powerschool.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.pieperj.powerschool.models.DatabaseStorageCallback;
import com.example.pieperj.powerschool.models.Reminder;
import com.example.pieperj.powerschool.models.ReminderDao;
import com.example.pieperj.powerschool.models.ReminderDatabase;

public class SaveReminderTask extends AsyncTask<Reminder, Void, Reminder> {

    private Context context;
    private DatabaseStorageCallback callback;

    public SaveReminderTask(Context context, DatabaseStorageCallback callback){
        this.callback = callback;
        this.context = context;
    }

    @Override
    protected Reminder doInBackground(Reminder... reminders) {
        ReminderDao dao = ReminderDatabase.getInstance().getDatabase(context).reminderDao();

        Reminder reminder = reminders[0];
        long id = dao.insert(reminder);

        reminder.setReminderId((int)id);

        return reminder;
    }


    @Override
    protected void onPostExecute(Reminder reminder) {
        callback.onDataStored(reminder);
    }
}
