package com.example.pieperj.powerschool.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.pieperj.powerschool.Assignment;
import com.example.pieperj.powerschool.Library;
import com.example.pieperj.powerschool.R;
import com.example.pieperj.powerschool.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddAssignmentFragment extends Fragment {

    private EditText enterAssignmentNameET, pointsTotalET, pointsEarnedET;
    private Button enterAssignmentBTN, submitAssignmentBTN;
    private TextView assignmentNameTV;
    private ArrayList<Assignment> assignments;



    public static final String TAG = "AddAssignmentFragment";


    ListView listView;

    public AddAssignmentFragment() {
        assignments = new ArrayList<>();

        for(int i = 0; i < Library.getInstance().getStudents().size(); i++) {
            assignments.add(new Assignment("", 0, 0));
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);

        listView = view.findViewById(R.id.LV_assignment_roster);

        listView.setAdapter(new CustomListAdapter(getActivity()));


        enterAssignmentNameET = view.findViewById(R.id.ET_enter_assignment_name);
        pointsTotalET = view.findViewById(R.id.ET_points_total);

        pointsEarnedET = view.findViewById(R.id.ET_assignment_points_earned);

        enterAssignmentBTN = view.findViewById(R.id.BTN_enter_assignment);

        assignmentNameTV = view.findViewById(R.id.TV_assignment_name);






        enterAssignmentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String assignmentName = enterAssignmentNameET.getText().toString();

                String assignmentPoints = (pointsTotalET.getText().toString());

                if(!assignmentPoints.isEmpty()) {
                    int possible = Integer.parseInt(assignmentPoints);
                    for(Assignment a : assignments) {
                        a.setPointsTotal(possible);
                        a.setName(assignmentName);
                    }
                }
                pointsTotalET.getText().clear();
                assignmentNameTV.setText("" + assignmentName);

                ((ArrayAdapter<Assignment>)listView.getAdapter()).notifyDataSetChanged();


            }
        });

        setHasOptionsMenu(true);
        return view;

        /*
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
        */


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_assignment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.option_save_assignment) {

            final int temp = assignments.get(0).getPointsEarned();
            assignments.get(0).setPointsEarned(0);


            Backendless.Persistence.of(Assignment.class).save(assignments.get(0), new AsyncCallback<Assignment>() {
                @Override
                public void handleResponse(Assignment response) {
                    assignments.get(0).setPointsEarned(temp);

                    Log.d(TAG, "" + assignments.get(0).toString() + " was saved");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.d(TAG, fault.toString());
                }
            });
            for(int i = 0; i < assignments.size(); i++) {


                Library.getInstance().getStudents().get(i).addAssignment(assignments.get(i));
                Library.getInstance().getStudents().get(i).calcTotalGrade();



                Log.d(TAG, "\n" + Library.getInstance().getStudents().get(i).getGrade());

                Log.d(TAG, "" + Library.getInstance().getStudents().get(i).getAssignments().size());
            }

        }


        return super.onOptionsItemSelected(item);
    }

    private class CustomListAdapter extends ArrayAdapter<Assignment> {

        private List<Student> students = Library.getInstance().getStudents();

        public CustomListAdapter(Context context) {
            super(context, -1, assignments);

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.assignment_custom_list_item, parent, false);
            }

            final Assignment current = assignments.get(position);
            TextView studentNameTV = convertView.findViewById(R.id.TV_assignment_student_name);
            studentNameTV.setText(students.get(position).getName());

            final EditText scoreET = convertView.findViewById(R.id.ET_assignment_points_earned);
            scoreET.setText(current.getPointsEarned() == 0 ? "" : String.valueOf(current.getPointsEarned()));


            scoreET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus && scoreET.getText().length() > 0) {
                        current.setPointsEarned(Integer.parseInt(scoreET.getText().toString()));
                    }
                }
            });

            TextView grade = convertView.findViewById(R.id.TV_assignment_grade);


            TextView pointsTotalTV = convertView.findViewById(R.id.TV_assignment_points_total);
            pointsTotalTV.setText(String.format(Locale.US, " / %d", assignments.get(position).getPointsTotal()));




            /*
            studentNameTV.setText("" + currentStudent.getName());
            grade.setText("" + currentStudent.getGrade() + "%");
            pointsEarned.setText("");
            //pointsTotal.setText("" + currentAssignment.getPointsTotal());
            */



            return convertView;

        }

        @Override
        public int getCount() {
            return assignments.size();
        }

    }

}
