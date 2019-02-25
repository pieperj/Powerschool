package com.example.pieperj.powerschool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

public class AddAssignmentFragment extends Fragment{

    private EditText enterAssignmentNameET, pointsTotalET, pointsEarnedET;
    private Button enterAssignmentBTN, submitAssignmentBTN;
    private TextView assignmentNameTV;
    private List<Assignment> assignments;
    private Assignment currentAssignment;
    private Student currentStudent;

    public static final String TAG = "AddAssignmentFragment";

    ArrayAdapter<Student> adapter;
    ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignments = new ArrayList<>();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);

        enterAssignmentNameET = view.findViewById(R.id.ET_enter_assignment_name);
        pointsTotalET = view.findViewById(R.id.ET_points_total);
        pointsEarnedET = view.findViewById(R.id.ET_assignment_points_earned);

        enterAssignmentBTN = view.findViewById(R.id.BTN_enter_assignment);
        submitAssignmentBTN = view.findViewById(R.id.BTN_submit_assignment);

        assignmentNameTV = view.findViewById(R.id.TV_assignment_name);

        listView = view.findViewById(R.id.LV_assignment_roster);
        adapter = new CustomAdapter();
        listView.setAdapter(adapter);


        currentAssignment = new Assignment();

        enterAssignmentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String assignmentName = enterAssignmentNameET.getText().toString();
                int assignmentPoints = Integer.parseInt(pointsTotalET.getText().toString());

                currentAssignment.setName(assignmentName);
                currentAssignment.setPointsTotal(assignmentPoints);

                assignmentNameTV.setText("" + currentAssignment);

                //should change values in each row, too

            }
        });


        submitAssignmentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String assignmentName = enterAssignmentNameET.getText().toString();
                final int pointsTotal = Integer.parseInt(pointsTotalET.getText().toString());

                currentAssignment = new Assignment(assignmentName, pointsTotal);
                //currentAssignment.setPointsTotal(pointsTotal);


                int pointsEarned = Integer.parseInt(pointsEarnedET.getText().toString());

                currentAssignment.setPointsEarned(pointsEarned);
                currentAssignment.setPointsTotal(pointsTotal);


                currentStudent.addAssignment(currentAssignment);



                Backendless.Persistence.save(currentAssignment, new AsyncCallback<Assignment>() {
                    @Override
                    public void handleResponse(Assignment response) {
                        Log.d(TAG, response.getName() + " was saved.");
                        Toast.makeText(getActivity(), "" + response.getName() + " was saved", Toast.LENGTH_SHORT).show();


                        adapter.notifyDataSetChanged();

                        currentAssignment = null;
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d(TAG, fault.toString());
                    }
                });

                assignments.add(currentAssignment);
                enterAssignmentNameET.setText("");
                pointsTotalET.setText("");

                assignmentNameTV.setText("" + currentAssignment);


            }
        });

        return view;
    }


    private class CustomAdapter extends ArrayAdapter<Student> {

        int[] scores;

        public CustomAdapter() {
            super(AddAssignmentFragment.this.getActivity(), R.layout.attendance_custom_list_item);
            scores = new int[getCount()];
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null) {
                convertView = LayoutInflater.from(AddAssignmentFragment.this.getActivity()).inflate(R.layout.assignment_custom_list_item, parent, false);
            }

            Log.d(TAG, "" + Library.getInstance().getStudents().get(position));

            currentStudent = Library.getInstance().getStudents().get(position);




            TextView studentNameTV = convertView.findViewById(R.id.TV_assignment_student_name);
            TextView grade = convertView.findViewById(R.id.TV_assignment_grade);


            final EditText pointsEarned = convertView.findViewById(R.id.ET_assignment_points_earned);

            pointsEarned.setText("" + scores[position]);

            pointsEarned.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        scores[position] = Integer.parseInt(pointsEarned.getText().toString());
                    }
                }

            });
            TextView pointsTotal = convertView.findViewById(R.id.TV_assignment_points_total);



            studentNameTV.setText("" + currentStudent.getName());
            grade.setText("" + currentStudent.getGrade() + "%");
            pointsEarned.setText("");
            //pointsTotal.setText("" + currentAssignment.getPointsTotal());


            notifyDataSetChanged();

            return convertView;

        }

        @Override
        public int getCount() {

            return Library.getInstance().getStudents().size();
        }

    }

}
