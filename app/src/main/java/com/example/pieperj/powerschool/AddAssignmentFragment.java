package com.example.pieperj.powerschool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private EditText enterAssignmentNameET, pointsTotalET;
    private Button enterAssignmentBTN;
    private TextView assignmentNameTV;
    private List<Assignment> assignments;
    private Assignment currentAssignment;
    private Student currentStudent;

    public static final String TAG = "AddAssignmentFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignments = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);

        enterAssignmentNameET = view.findViewById(R.id.ET_enter_assignment_name);
        pointsTotalET = view.findViewById(R.id.ET_points_total);
        enterAssignmentBTN = view.findViewById(R.id.BTN_enter_assignment);

        assignmentNameTV = view.findViewById(R.id.TV_assignment_name);

        final ListView listView = view.findViewById(R.id.LV_assignment_roster);
        ArrayAdapter<Student> adapter = new CustomAdapter();
        listView.setAdapter(adapter);


        enterAssignmentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String assignmentName = enterAssignmentNameET.getText().toString();
                int pointsTotal = Integer.parseInt(pointsTotalET.getText().toString());

                currentAssignment = new Assignment(assignmentName, pointsTotal);

                Backendless.Persistence.save(currentAssignment, new AsyncCallback<Assignment>() {
                    @Override
                    public void handleResponse(Assignment response) {
                        Log.d(TAG, response.getName() + " was saved.");
                        Toast.makeText(getActivity(), "" + response.getName() + " was saved", Toast.LENGTH_SHORT).show();



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

        public CustomAdapter() {
            super(AddAssignmentFragment.this.getActivity(), R.layout.attendance_custom_list_item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null) {
                convertView = LayoutInflater.from(AddAssignmentFragment.this.getActivity()).inflate(R.layout.assignment_custom_list_item, parent, false);
            }

            currentStudent = Library.getInstance().getStudents().get(position);

            currentAssignment = LibraryAssignment.getInstance().getLibrary()
                                            .get(LibraryAssignment.getInstance().getLibrary().size() - 1);


            TextView studentNameTV = convertView.findViewById(R.id.TV_assignment_student_name);
            TextView grade = convertView.findViewById(R.id.TV_assignment_grade);
            EditText pointsEarned = convertView.findViewById(R.id.TV_assignment_points_earned);
            TextView pointsTotal = convertView.findViewById(R.id.TV_assignment_points_total);



            studentNameTV.setText("" + currentStudent.getName());
            grade.setText("" + currentStudent.getGrade() + "%");
            pointsEarned.setText("");
            pointsTotal.setText("" + currentAssignment.getPointsTotal());


            notifyDataSetChanged();

            return convertView;

        }

        @Override
        public int getCount() {
            return Library.getInstance().getStudents().size();
        }

    }

}
