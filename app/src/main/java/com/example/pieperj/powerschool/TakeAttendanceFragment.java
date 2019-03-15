package com.example.pieperj.powerschool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TakeAttendanceFragment extends Fragment {

    Student currentStudent = null;
    Button submitAttendanceButton;


    public static final String TAG = "TakeAttendanceFragment";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_attendance, container, false);

        submitAttendanceButton = view.findViewById(R.id.BTN_submit_attendance);

        /*
        Library.getInstance().getStudents().get(0).setAttendence(95.7); //preset students already in class
        Library.getInstance().getStudents().get(1).setAttendence(76.3);
        Library.getInstance().getStudents().get(2).setAttendence(89.2);
        Library.getInstance().getStudents().get(3).setAttendence(99.3);
        */
        
        final ListView listView = view.findViewById(R.id.LV_attendence_roster);
        final ArrayAdapter<Student> adapter = new CustomAdapter();
        listView.setAdapter(adapter);

        submitAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < listView.getCount(); i++) {
                    CheckBox cb = listView.getChildAt(i).findViewById(R.id.CB_attendance_isAbsent);

                    Student student = Library.getInstance().getStudents().get(i);
                    student.addDayTotal();

                    if(cb.isChecked() == false) {

                        student.addDayMissed();

                        /***/
                        student.setAttendence((double)(student.getDaysTotal() - student.getDaysMissed())/(student.getDaysTotal()));

                        int totalDays = Library.getInstance().getTotalDays();
                        Log.d(TAG, "Total Days" + totalDays);

                        int daysAttended = totalDays - student.getDaysMissed();
                        Log.d(TAG, "Days Attended" + daysAttended);

                        // NOT WORKING
                        student.setAttendence(((student.getAttendence() + ((double)daysAttended / (double)totalDays)) / 2));

                        adapter.notifyDataSetChanged();
                    }


                }



            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save_attendance, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
    }

    private class CustomAdapter extends ArrayAdapter<Student> {

        public CustomAdapter() {
            super(TakeAttendanceFragment.this.getActivity(), R.layout.attendance_custom_list_item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null) {
                convertView = LayoutInflater.from(TakeAttendanceFragment.this.getActivity()).inflate(R.layout.attendance_custom_list_item, parent, false);
            }

            Log.d(TAG, "" + Library.getInstance().getStudents().size());

            currentStudent = Library.getInstance().getStudents().get(position);


            Log.d(TAG, "" + Library.getInstance().getStudents().get(position));
            TextView studentNameTV = convertView.findViewById(R.id.TV_attendance_student_name);
            TextView gradeLevelTV = convertView.findViewById(R.id.TV_attendance_year);
            TextView attendenceTV = convertView.findViewById(R.id.TV_attendance_attendence);
            CheckBox isPresentCheckBox = convertView.findViewById(R.id.CB_attendance_isAbsent);
            isPresentCheckBox.setChecked(true);


            studentNameTV.setText(currentStudent.getName());
            gradeLevelTV.setText("" + currentStudent.getYear());
            attendenceTV.setText(String.format("%.1f%%", currentStudent.getAttendence()));

            notifyDataSetChanged();

            return convertView;

        }

        @Override
        public int getCount() {

            return Library.getInstance().getStudents().size();

        }
    }

}
