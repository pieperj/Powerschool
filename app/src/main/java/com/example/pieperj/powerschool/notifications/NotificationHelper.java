package com.example.pieperj.powerschool.notifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.pieperj.powerschool.models.Reminder;
public class NotificationHelper {

    public static final int REQUEST_NOTIFICATION = 100;
    public static final int REQUEST__SCHEDULE_NOTIFICATION = 101;

    public static final String EXTRA_NOTIFICATION_TITLE = "Notification Title";
    public static final String EXTRA_NOTIFICATION_DESCRIPTION = "Notification Description";


    public static final String NOTIFICATION_CHANNEL_ID = "Reminders";
    public static final String NOTIFICATION_CHANNEL_NAME = "Reminders";



    public static void createNotificationChannel(Context context) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


    public static void scheduleReminder(Context context, Reminder reminder) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_TITLE, reminder.getTitle());
        intent.putExtra(NotificationHelper.EXTRA_NOTIFICATION_DESCRIPTION, reminder.getDescription());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST__SCHEDULE_NOTIFICATION,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        manager.setExact(AlarmManager.RTC_WAKEUP, reminder.getTime(), pendingIntent);
    }

}
