package com.example.pieperj.powerschool.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.pieperj.powerschool.R;
import com.example.pieperj.powerschool.models.Reminder;
import com.example.pieperj.powerschool.tasks.UpdateReminderTask;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Reminder> reminders;

    public NotificationRecyclerViewAdapter(Context context, List<Reminder> reminders) {
        this.context = context;
        this.reminders = reminders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.notification_recycler_view_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Reminder reminder = reminders.get(position);

        holder.titleTextView.setText(reminder.getTitle());
        holder.descriptionTextView.setText(reminder.getDescription());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(reminder.getTime());



        holder.dateTextView.setText(cal.getTime().toString());


    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }


    public void markAsDone(int position) {

        Reminder reminder = reminders.remove(position);
        reminder.setComplete(true);

        new UpdateReminderTask(context).execute(reminder);

        notifyDataSetChanged();

    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView, descriptionTextView, dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notification_title);
            descriptionTextView = itemView.findViewById(R.id.notification_description);
            dateTextView = itemView.findViewById(R.id.notification_date);
        }
    }
}
