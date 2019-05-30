package com.example.pieperj.powerschool.notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.pieperj.powerschool.activities.StudentActivity;
import com.example.pieperj.powerschool.models.DatabaseRetrievalCallback;
import com.example.pieperj.powerschool.tasks.GetRemindersTask;
import com.example.pieperj.powerschool.R;
import com.example.pieperj.powerschool.models.Reminder;


import java.util.Calendar;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    public static int notificationId = 0;

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("NotificationReceiver", "received broadcast from boot complete");

            new GetRemindersTask(context, new DatabaseRetrievalCallback() {
                @Override
                public void onDataRetrieved(List<Reminder> reminders) {

                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    for(Reminder reminder : reminders) {
                        if (currentTime < reminder.getTime()) {
                            NotificationHelper.scheduleReminder(context, reminder);
                        }
                    }

                }
            }, false).execute();


        }

        else {
            String title = intent.getStringExtra(NotificationHelper.EXTRA_NOTIFICATION_TITLE);
            String description = intent.getStringExtra(NotificationHelper.EXTRA_NOTIFICATION_DESCRIPTION);

            Intent openAppIntent = new Intent(context, StudentActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.NOTIFICATION_CHANNEL_ID);

            builder.setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);


            NotificationManagerCompat manager = NotificationManagerCompat.from(context);

            manager.notify(notificationId, builder.build());
            notificationId++;

        }
    }



}
