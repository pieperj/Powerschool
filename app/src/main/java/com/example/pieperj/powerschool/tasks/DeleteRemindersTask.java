package com.example.pieperj.powerschool.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.pieperj.powerschool.models.Reminder;
import com.example.pieperj.powerschool.models.ReminderDao;
import com.example.pieperj.powerschool.models.ReminderDatabase;

import java.util.Locale;

public class DeleteRemindersTask extends AsyncTask<Reminder, Void, Integer> {

    private Context context;

    public DeleteRemindersTask(Context context){
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Reminder... reminders) {
        ReminderDao dao = ReminderDatabase.getInstance().getDatabase(context).reminderDao();

        for(Reminder reminder : reminders){
            dao.delete(reminder);
        }
        return reminders.length;
    }

    @Override
    protected void onPostExecute(Integer amount) {
        Toast.makeText(context, String.format(Locale.US, "%d reminders deleted", amount), Toast.LENGTH_SHORT).show();
    }
}
