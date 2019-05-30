package com.example.pieperj.powerschool.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.pieperj.powerschool.R;
import com.example.pieperj.powerschool.models.DatabaseStorageCallback;
import com.example.pieperj.powerschool.models.RecyclerListAdapter;
import com.example.pieperj.powerschool.models.Reminder;
import com.example.pieperj.powerschool.models.ReminderDatabase;
import com.example.pieperj.powerschool.notifications.NotificationHelper;
import com.example.pieperj.powerschool.tasks.SaveReminderTask;
import com.example.pieperj.powerschool.views.DatePickerDialogFragment;
import com.example.pieperj.powerschool.views.TimePickerDialogFragment;

import java.util.Calendar;

public class SetReminderFragment extends Fragment implements DatabaseStorageCallback, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {


    private EditText reminderTitleET, reminderDescriptionET;
    private Button setTimeBTN, setDateBTN, saveReminderBTN;
    private Calendar cal;

    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter = new RecyclerListAdapter();

    public SetReminderFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_reminder, container, false);

        cal = Calendar.getInstance();

        reminderTitleET = view.findViewById(R.id.ET_reminder_title);
        reminderDescriptionET = view.findViewById(R.id.ET_reminder_description);

        setDateBTN = view.findViewById(R.id.BTN_set_reminder_date);
        setTimeBTN = view.findViewById(R.id.BTN_set_reminder_time);
        saveReminderBTN = view.findViewById(R.id.BTN_save_reminder);

        recyclerView = view.findViewById(R.id.rv_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(decoration);


        setDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new DatePickerDialogFragment();
                fragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        setTimeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new TimePickerDialogFragment();
                fragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });


        saveReminderBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reminder reminder = new Reminder(reminderTitleET.getText().toString(), reminderDescriptionET.getText().toString(),
                        cal.getTimeInMillis());

                Log.d("AddReminderActivity", "saving: " + ReminderDatabase.getInstance().getAllReminders().toString());

                NotificationHelper.scheduleReminder(getActivity(), reminder);
                NotificationHelper.createNotificationChannel(getActivity());
                new SaveReminderTask(getActivity(), SetReminderFragment.this).execute(reminder);



            }
        });

        //new GetRemindersTask(getActivity(), SetReminderFragment.this, false).execute();

        return view;
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        cal.set(year, month, dayOfMonth);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
    }


    @Override
    public void onDataStored(Reminder reminder) {
        ReminderDatabase.getInstance().addReminder(reminder);
        getActivity().finish();
    }

}
