package com.example.pieperj.powerschool.tasks;

import android.content.Context;
import android.os.AsyncTask;


import com.example.pieperj.powerschool.models.Reminder;
import com.example.pieperj.powerschool.models.ReminderDao;
import com.example.pieperj.powerschool.models.ReminderDatabase;

public class UpdateReminderTask extends AsyncTask<Reminder, Void, Void> {

    private Context context;

    public UpdateReminderTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Reminder... reminders) {
        ReminderDao dao = ReminderDatabase.getInstance().getDatabase(context).reminderDao();

        Reminder reminder = reminders[0];

        dao.update(reminder);

        return null;

    }


}
