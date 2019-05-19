package com.example.pieperj.powerschool.tasks;

import android.content.Context;
import android.os.AsyncTask;


import com.example.pieperj.powerschool.models.DatabaseRetrievalCallback;
import com.example.pieperj.powerschool.models.Reminder;
import com.example.pieperj.powerschool.models.ReminderDao;
import com.example.pieperj.powerschool.models.ReminderDatabase;

import java.util.List;

public class GetRemindersTask extends AsyncTask<Void, Void, List<Reminder>> {

    private Context context;
    private DatabaseRetrievalCallback callback;
    private boolean isComplete;

    public GetRemindersTask(Context context, DatabaseRetrievalCallback callback, boolean isComplete) {
        this.context = context;
        this.callback = callback;
        this.isComplete = isComplete;
    }
    

    public GetRemindersTask(Context context){
        this.context = context;
    }

    @Override
    protected List<Reminder> doInBackground(Void... voids) {
        ReminderDao dao = ReminderDatabase.getInstance().getDatabase(context).reminderDao();
        return dao.getReminders(isComplete);
    }

    @Override
    protected void onPostExecute(List<Reminder> reminders) {
        callback.onDataRetrieved(reminders);
    }
}
